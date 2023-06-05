package com.yevini.myvelog.util.redis;

import com.yevini.myvelog.model.velog.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class UserRedisUtil {

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
