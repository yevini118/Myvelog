package com.yevini.myvelog.request;

import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.UserTags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WebClientServiceTest {

    @InjectMocks
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