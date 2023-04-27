package com.yevini.myvelog.request;

import com.yevini.myvelog.request.body.RequestBody;
import com.yevini.myvelog.request.body.variables.PostsVariables;
import com.yevini.myvelog.request.body.variables.StatsVariables;
import com.yevini.myvelog.request.body.variables.UserTagsVariables;
import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stats;
import com.yevini.myvelog.response.UserTags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class VelogService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String REQUEST_URL="https://v2cdn.velog.io/graphql";

    public UserTags getUserTags(String username){

        UserTagsVariables variables = UserTagsVariables.builder()
                .username(username)
                .build();

        RequestBody body = RequestBody.builder()
                .operationName("UserTags")
                .variables(variables)
                .query("query UserTags($username: String) {\n  userTags(username: $username) {\n    tags {\n      id\n      name\n      posts_count\n   }\n    posts_count\n    }\n}\n")
                .build();

        return restTemplate.postForObject(REQUEST_URL, body, UserTags.class);
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
                .query("query Posts($cursor: ID, $username: String, $temp_only: Boolean, $tag: String, $limit: Int) {\n  posts(cursor: $cursor, username: $username, temp_only: $temp_only, tag: $tag, limit: $limit) {\n    id\n    title\n    thumbnail\n       comments_count\n    tags\n    likes\n   }\n}\n")
                .build();

        return restTemplate.postForObject(REQUEST_URL, body, Posts.class);
    }

    public Stats getStats(String postId, String accessToken) {

        StatsVariables variables = StatsVariables.builder()
                .postId(postId)
                .build();

        RequestBody body = RequestBody.builder()
                .operationName("GetStats")
                .variables(variables)
                .query("query GetStats($post_id: ID!) {\n  getStats(post_id: $post_id) {\n    total\n    count_by_day {\n      count\n      day\n      __typename\n    }\n    __typename\n  }\n}\n")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("cookie", accessToken);

        HttpEntity<RequestBody> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(REQUEST_URL, request, Stats.class);
    }
}
