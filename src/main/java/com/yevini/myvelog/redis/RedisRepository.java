package com.yevini.myvelog.redis;

import com.yevini.myvelog.response.User;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<User, String> {
}
