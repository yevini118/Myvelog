package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.global.security.CustomUserDetails;
import com.yevini.myvelog.global.security.VelogAuthenticationToken;
import com.yevini.myvelog.global.util.JwtUtil;
import com.yevini.myvelog.global.util.redis.UserRedisUtil;
import com.yevini.myvelog.model.velog.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final JwtUtil jwtUtil;
    private final SeleniumService seleniumService;

    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRedisUtil userRedisUtil;

    public String login() throws IOException {

        return "username";
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new CustomUserDetails(userRedisUtil.get(username));
    }
}
