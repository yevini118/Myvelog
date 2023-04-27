package com.yevini.myvelog.velog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.crawling.SeleniumService;
import com.yevini.myvelog.redis.JwtService;
import com.yevini.myvelog.redis.RedisService;
import com.yevini.myvelog.request.VelogService;
import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stats;
import com.yevini.myvelog.response.User;
import com.yevini.myvelog.response.UserTags;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.NoSuchWindowException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class MyvelogService {

    private final JwtService jwtService;
    private final RedisService redisService;
    private final SeleniumService seleniumService;
    private final VelogService velogService;

    public void login() throws JsonProcessingException {

        User user = seleniumService.process();

        Duration duration = jwtService.getDurationLeft(user.getAccessToken());
        redisService.set(user, duration);
    }

    public void main(String username) {

        User user = redisService.get(username);

        UserTags userTags = velogService.getUserTags(username);
        Posts posts = velogService.getPosts(username, userTags.getTotalPostsCount());
        Stats stats = velogService.getStats(username, user.getAccessToken());
    }

}
