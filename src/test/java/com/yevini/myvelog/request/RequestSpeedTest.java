package com.yevini.myvelog.request;

import com.yevini.myvelog.response.Post;
import com.yevini.myvelog.response.Stat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RequestSpeedTest {

    @Autowired
    RestTemplateService restTemplateService;
    @Autowired
    WebClientService webClientService;

    private static List<Post> posts;
    private final String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZGMyNjhlYzMtMDc0Yy00NDI5LTgyY2EtNjY4M2JlODFlOWQzIiwiaWF0IjoxNjgzNjk3MzM2LCJleHAiOjE2ODM3MDA5MzYsImlzcyI6InZlbG9nLmlvIiwic3ViIjoiYWNjZXNzX3Rva2VuIn0.zLRTNZHOgsxOsRjN2vclQBazeKhLmsGBTfIhuJCm8rY";

    @BeforeAll
    public static void setUp(@Autowired WebClientService webClientService) {
        posts = webClientService.getPosts("yevini118", 50).getPosts();
    }

    @Test
    public void RestTemplate_속도() {

        List<Stat> stats = restTemplateService.getStats(posts, accessToken);

        Assertions.assertEquals(stats.size(), posts.size());
    }

    @Test
    public void WebClient_비동기_속도() {

        List<Stat> stats = webClientService.getStats(posts, accessToken);

        Assertions.assertEquals(stats.size(), posts.size());
    }

    @Test
    public void WebClient_동기_속도() {

        List<Stat> stats = webClientService.getStatsByBlock(posts, accessToken);

        Assertions.assertEquals(stats.size(), posts.size());
    }

}
