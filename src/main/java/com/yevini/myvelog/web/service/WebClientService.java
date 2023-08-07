package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevini.myvelog.model.monbodb.Subscribe;
import com.yevini.myvelog.model.response.*;
import com.yevini.myvelog.model.velog.User;
import com.yevini.myvelog.web.dto.SubscribeResponseDto;
import com.yevini.myvelog.web.dto.request.RequestBody;
import com.yevini.myvelog.web.dto.request.variables.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebClientService {

    private final WebClient webClient;

    public boolean isUserProfileExists(String username) {

        ResponseEntity<Void> responseEntity = webClient.mutate().baseUrl("https://velog.io/@" + username).build()
                .get()
                .retrieve()
                .toEntity(Void.class)
                .onErrorComplete()
                .block();

        return responseEntity != null;
    }


    public List<SubscribeResponseDto> getUserProfile(List<Subscribe> subscribes) {

        CountDownLatch countDownLatch = new CountDownLatch(subscribes.size());

        List<SubscribeResponseDto> responseDtos = Collections.synchronizedList(new ArrayList<>());
        for (Subscribe subscribe : subscribes) {

            Variables variables = new UserProfileVariables(subscribe.getUsername());

            RequestBody body = RequestBody.builder()
                    .operationName("UserProfile")
                    .variables(variables)
                    .query("""
                        query UserProfile($username: String!) {
                            user(username: $username) {
                                username
                                profile {
                                    display_name
                                    thumbnail
                                }
                            }
                        }
                        """)
                    .build();

            webClient
                    .post()
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(UserProfile.class)
                    .doOnTerminate(countDownLatch::countDown)
                    .subscribe(userProfile -> {
                        responseDtos.add(new SubscribeResponseDto(subscribe.getId(), userProfile));
                    });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return responseDtos;
    }

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

        return webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(UserTags.class)
                .block();
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
                                url_slug
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

    public List<Stat> getStats(List<Post> posts, String accessToken){

        CountDownLatch countDownLatch = new CountDownLatch(posts.size());

        List<Stat> stats = Collections.synchronizedList(new ArrayList<>());
        for (Post post : posts) {

            Variables variables = new StatsVariables(post.getId());

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

            getWebClientAdded(accessToken)
                .post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Stat.class)
                .doOnTerminate(countDownLatch::countDown)
                .subscribe(result -> {
                    result.setId(post.getId());
                    stats.add(result);
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
        for (Post post : posts) {

            Variables variables = new StatsVariables(post.getId());

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

            stats.add(getWebClientAdded(accessToken)
                    .post()
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Stat.class)
                    .block());
        }

        return stats;
    }

    private WebClient getWebClientAdded(String accessToken) {
        return webClient.mutate()
                .defaultCookie("access_token", accessToken)
                .build();
    }
}
