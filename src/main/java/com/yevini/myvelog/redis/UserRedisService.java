package com.yevini.myvelog.redis;

import com.yevini.myvelog.response.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserRedisService {

    private final RedisTemplate<String, User> redisTemplate;
    public void set(User user, Duration duration) {

        redisTemplate.opsForValue().set(user.getUsername(), user, duration);
    }

    public User get(String username) {

        return redisTemplate.opsForValue().get(username);
    }

    public void delete(String username) {

        redisTemplate.delete(username);
    }

}
