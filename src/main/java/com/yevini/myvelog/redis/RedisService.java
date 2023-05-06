package com.yevini.myvelog.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevini.myvelog.response.CurrentUser;
import com.yevini.myvelog.response.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, User> redisTemplate;
    public void set(User user, Duration duration) {

        String key = user.getUsername();
        redisTemplate.opsForValue().set(key, user, duration);
    }

    public User get(String key) {

        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {

        redisTemplate.delete(key);
    }

}
