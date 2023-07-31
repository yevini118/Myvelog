package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.global.security.VelogAuthenticationToken;
import com.yevini.myvelog.global.util.JwtUtil;
import com.yevini.myvelog.global.util.redis.UserRedisUtil;
import com.yevini.myvelog.model.velog.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final SeleniumService seleniumService;

    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRedisUtil userRedisUtil;

    public void login() throws IOException {

        User user = seleniumService.login();

        VelogAuthenticationToken authenticationToken = user.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        Duration duration = jwtUtil.getDurationLeft(user.getAccessToken());
        userRedisUtil.set(user, duration);
    }

//    public String login() throws JsonProcessingException, IOException {
//
//        User user = seleniumService.login();
//
//        Duration duration = jwtUtil.getDurationLeft(user.getAccessToken());
//        userRedisUtil.set(user, duration);
//
//        return user.getUsername();
//    }

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
