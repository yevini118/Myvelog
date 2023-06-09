package com.yevini.myvelog.request;

import com.yevini.myvelog.model.response.Post;
import com.yevini.myvelog.model.response.Stat;
import com.yevini.myvelog.web.service.RestTemplateService;
import com.yevini.myvelog.web.service.WebClientService;
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
    private final String accessToken = "";

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
