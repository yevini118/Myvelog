package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.util.JwtUtil;
import com.yevini.myvelog.util.redis.UserRedisUtil;
import com.yevini.myvelog.model.velog.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final SeleniumService seleniumService;
    private final UserRedisUtil userRedisUtil;

    public String login() throws JsonProcessingException {

        User user = seleniumService.process();

        Duration duration = jwtUtil.getDurationLeft(user.getAccessToken());
        userRedisUtil.set(user, duration);

        return user.getUsername();
    }

    public void logout(String username) {

        userRedisUtil.delete(username);
    }

    public User getUser(String username) {

        User user = userRedisUtil.get(username);

        if (user == null) {
            throw new IllegalArgumentException();
        }
        return user;
    }
}
