package com.yevini.myvelog.request;

import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stat;
import com.yevini.myvelog.response.UserTags;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RequestSpeedTest {

    @InjectMocks
    RestTemplateService restTemplateService;
    WebClientService webClientService;

    private UserTags userTags = webClientService.getUserTags("yevini118");
    private Posts posts = webClientService.getPosts("yevini118", userTags.getTotalPostsCount());
    private String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZGMyNjhlYzMtMDc0Yy00NDI5LTgyY2EtNjY4M2JlODFlOWQzIiwiaWF0IjoxNjgzMTIzMjEzLCJleHAiOjE2ODMxMjY4MTMsImlzcyI6InZlbG9nLmlvIiwic3ViIjoiYWNjZXNzX3Rva2VuIn0.IToBVlUTsybH3Eg06IpJZjufV59y9UbHDLAlYgyunN8";

    @Test
    public void RestTemplate_속도() {

        long start = System.currentTimeMillis();
        List<Stat> stats = restTemplateService.getStats(posts.getPosts(), accessToken);
        long end = System.currentTimeMillis();

        Assertions.assertEquals(stats.size(), posts.getPosts().size());
        System.out.println("RestTemplate : " + (end-start) + "ms");
    }

    @Test
    public void WebClient_비동기_속도() {

        long start = System.currentTimeMillis();
        List<Stat> stats = webClientService.getStats(posts.getPosts(), accessToken);
        long end = System.currentTimeMillis();

        Assertions.assertEquals(stats.size(), posts.getPosts().size());
        System.out.println("WebClient_NonBlock : " + (end-start) + "ms");
    }

    @Test
    public void WebClient_동기_속도() {

        long start = System.currentTimeMillis();
        List<Stat> stats = webClientService.getStatsByBlock(posts.getPosts(), accessToken);
        long end = System.currentTimeMillis();

        Assertions.assertEquals(stats.size(), posts.getPosts().size());
        System.out.println("WebClient_Block : " + (end-start) + "ms");
    }

}
