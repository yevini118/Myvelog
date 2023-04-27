package com.yevini.myvelog.request;

import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.UserTags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VelogServiceTest {

    @InjectMocks
    VelogService velogService;

    @Test
    void userTags_가져오기() {

        //given
        String username = "yevini118";

        //when
        UserTags userTags = velogService.getUserTags(username);

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
        Posts posts = velogService.getPosts(username, totalPostsCount);

        //then
        assertEquals(posts.getPosts().size(), 10);
    }

    @Test
    void getStats() {

        //given
        String postId = "bfd1061d-c564-43b4-a0c5-615546eb65e7";
        String accessToken = "";
    }
}