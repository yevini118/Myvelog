package com.yevini.myvelog.util.redis;

import com.yevini.myvelog.model.velog.MyvelogStats;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StatsRedisUtil {

    private final RedisTemplate<String, MyvelogStats> redisTemplate;
    private static final String PREFIX = "STAT_";

    public void set(String username, MyvelogStats stats) {

        redisTemplate.opsForValue().set(getKey(username), stats);
    }

    public MyvelogStats get(String username) {

        return redisTemplate.opsForValue().get(getKey(username));
    }

    private String getKey(String username) {

        return PREFIX + username;
    }
}
