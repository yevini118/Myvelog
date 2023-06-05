package com.yevini.myvelog.web.service;

import com.yevini.myvelog.web.dto.request.RequestBody;
import com.yevini.myvelog.web.dto.request.variables.PostsVariables;
import com.yevini.myvelog.web.dto.request.variables.StatsVariables;
import com.yevini.myvelog.web.dto.request.variables.UserTagsVariables;
import com.yevini.myvelog.web.dto.request.variables.Variables;
import com.yevini.myvelog.model.response.Post;
import com.yevini.myvelog.model.response.Posts;
import com.yevini.myvelog.model.response.Stat;
import com.yevini.myvelog.model.response.UserTags;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class RestTemplateService {

    private final RestTemplate restTemplate;

    private static final String REQUEST_URL="https://v2cdn.velog.io/graphql";

    public UserTags getUserTags(String username){

        Variables variables = new UserTagsVariables(username);

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

        return restTemplate.postForObject(REQUEST_URL, body, UserTags.class);
    }

    public Posts getPosts(String username, int totalPostsCount) {

        Variables variables = PostsVariables.builder()
                .username(username)
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

        return restTemplate.postForObject(REQUEST_URL, body, Posts.class);
    }

    public List<Stat> getStats(List<Post> posts, String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("cookie", "access_token=" + accessToken);

        List<Stat> stats = new ArrayList<>();
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

            HttpEntity<RequestBody> entity = new HttpEntity<>(body, headers);

            Stat stat = restTemplate.postForObject(REQUEST_URL, entity, Stat.class);
            stat.setId(post.getId());
            stats.add(stat);
        }

        return stats;
    }
}
