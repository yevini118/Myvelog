package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.global.util.JwtUtil;
import com.yevini.myvelog.global.util.redis.UserRedisUtil;
import com.yevini.myvelog.model.velog.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final SeleniumService seleniumService;
    private final UserRedisUtil userRedisUtil;

    public String login() throws JsonProcessingException, IOException {

        User user = seleniumService.login();

        Duration duration = jwtUtil.getDurationLeft(user.getAccessToken());
        userRedisUtil.set(user, duration);

        return user.getUsername();
    }

    public void logout(String username) {

        seleniumService.logout();
        userRedisUtil.delete(username);
    }

    public User getUser(String username) {

        User user = userRedisUtil.get(username);

        checkUserNull(user);

        return user;
    }

    private static void checkUserNull(User user) {
        
        if (user == null) {
            throw new IllegalArgumentException();
        }
    }
}
