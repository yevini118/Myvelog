package com.yevini.myvelog.web.service;

import com.yevini.myvelog.global.util.JwtUtil;
import com.yevini.myvelog.global.util.redis.UserRedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserRedisUtil userRedisUtil;
    @Mock
    private SeleniumService seleniumService;


    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void getUser() {
    }
}