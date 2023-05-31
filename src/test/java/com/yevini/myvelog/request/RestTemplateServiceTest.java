package com.yevini.myvelog.request;

import com.yevini.myvelog.web.dto.response.Posts;
import com.yevini.myvelog.web.dto.response.UserTags;
import com.yevini.myvelog.web.service.RestTemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestTemplateServiceTest {

    @InjectMocks
    private RestTemplateService restTemplateService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void userTags_가져오기() {

        //given
        String username = "yevini118";

        //when
        UserTags userTags = restTemplateService.getUserTags(username);

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
        Posts posts = restTemplateService.getPosts(username, totalPostsCount);

        //then
        assertEquals(posts.getPosts().size(), totalPostsCount);
    }


}