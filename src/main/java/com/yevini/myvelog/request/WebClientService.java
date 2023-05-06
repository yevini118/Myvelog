package com.yevini.myvelog.request;

import com.yevini.myvelog.request.body.RequestBody;
import com.yevini.myvelog.request.body.variables.PostsVariables;
import com.yevini.myvelog.request.body.variables.StatsVariables;
import com.yevini.myvelog.request.body.variables.UserTagsVariables;
import com.yevini.myvelog.response.Post;
import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stat;
import com.yevini.myvelog.response.UserTags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RequiredArgsConstructor
@Service
public class WebClientService {

    private final WebClient webClient;

    public UserTags getUserTags(String username){

        UserTagsVariables variables = new UserTagsVariables(username);

        RequestBody body = RequestBody.builder()
                .operationName("UserTags")
                .variables(variables)
                .query("""
                        query UserTags($username: String) {
                           userTags(username: $username) {
                               tags {
                                   id
                                   name
                                   posts_count
                               }
                           posts_count
                           }
                        }
                        """)
                .build();

        return webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(UserTags.class)
                .block();
    }

    public Posts getPosts(String username, int totalPostsCount) {

        PostsVariables variables = PostsVariables.builder()
                .username(username)
                .tag(null)
                .limit(totalPostsCount)
                .build();

        RequestBody body = RequestBody.builder()
                .operationName("Posts")
                .variables(variables)
                .query("""
                        query Posts($cursor: ID, $username: String, $temp_only: Boolean, $tag: String, $limit: Int) {
                            posts(cursor: $cursor, username: $username, temp_only: $temp_only, tag: $tag, limit: $limit) {
                                id
                                title
                                thumbnail
                                comments_count
                                tags
                                likes
                           }
                        }
                        """)
                .build();

        return webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Posts.class)
                .block();
    }

    public List<Stat> getStats(List<Post> posts, String accessToken) {

        List<Stat> stats = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(posts.size());

        for (Post post : posts) {

            StatsVariables variables = new StatsVariables(post.getId());

            RequestBody body = RequestBody.builder()
                    .operationName("GetStats")
                    .variables(variables)
                    .query("query GetStats($post_id: ID!) {\n  getStats(post_id: $post_id) {\n    total\n    count_by_day {\n      count\n      day\n      __typename\n    }\n    __typename\n  }\n}\n")
                    .build();

            webClient.mutate()
                    .defaultCookie("access_token", accessToken)
                    .build()
                    .post()
                    .bodyValue(body)
                    .retrieve()
                    .toEntity(Stat.class)
                    .subscribe(result -> {
                        System.out.println(result.getStatusCode());
                        result.getBody().setId(post.getId());
                        stats.add(result.getBody());
                        countDownLatch.countDown();
                    });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return stats;
    }

    public List<Stat> getStatsByBlock(List<Post> posts, String accessToken) {

        List<Stat> stats = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(posts.size());

        for (Post post : posts) {

            StatsVariables variables = new StatsVariables(post.getId());

            RequestBody body = RequestBody.builder()
                    .operationName("GetStats")
                    .variables(variables)
                    .query("""
                            query GetStats($post_id: ID!) {
                                getStats(post_id: $post_id) {
                                    total
                                    count_by_day {
                                        count
                                        day
                                    }
                                }
                            }
                            """)
                    .build();

            stats.add(webClient.mutate()
                    .defaultCookie("access_token", accessToken)
                    .build()
                    .post()
                    .bodyValue(body)
                    .retrieve()
                    .toEntity(Stat.class)
                    .block()
                    .getBody());

        }

        return stats;
    }
}
