package com.yevini.myvelog.request;

import com.yevini.myvelog.model.response.Posts;
import com.yevini.myvelog.model.response.UserTags;
import com.yevini.myvelog.web.service.WebClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebClientServiceTest {

    @Autowired
    WebClientService webClientService;

    @Test
    void userTags_가져오기() {

        //given
        String username = "yevini118";

        //when
        UserTags userTags = webClientService.getUserTags(username);

        //then
        assertTrue(userTags.getTotalPostsCount() >= 0);
        assertNotNull(userTags.getTags());
    }

    @Test
    void posts_가져오기() {

        //given
        String username = "yevini118";
        int totalPostsCount = 10;

        //when
        Posts posts = webClientService.getPosts(username, totalPostsCount);

        //then
        assertEquals(posts.getPosts().size(), 10);
    }
}