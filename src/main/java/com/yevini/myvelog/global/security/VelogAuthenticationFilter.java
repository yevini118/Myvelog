package com.yevini.myvelog.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevini.myvelog.global.util.JwtUtil;
import com.yevini.myvelog.global.util.redis.UserRedisUtil;
import com.yevini.myvelog.model.velog.User;
import com.yevini.myvelog.web.service.SeleniumService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class VelogAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final SeleniumService seleniumService;
    private final JwtUtil jwtUtil;
    private final UserRedisUtil userRedisUtil;

    public VelogAuthenticationFilter(String defaultFilterProcessesUrl, SeleniumService seleniumService, JwtUtil jwtUtil, UserRedisUtil userRedisUtil) {
        super(defaultFilterProcessesUrl);
        this.seleniumService = seleniumService;
        this.jwtUtil = jwtUtil;
        this.userRedisUtil = userRedisUtil;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

//        ServletInputStream inputStream = request.getInputStream();
//        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//        ObjectMapper objectMapper = new ObjectMapper();
//        LoginRequestDto loginRequestDto = objectMapper.readValue(messageBody, LoginRequestDto.class);

        String sns = request.getParameter("sns");
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        LoginRequestDto loginRequestDto = new LoginRequestDto(sns, id, password);

        User user = null;
        try {
            user = seleniumService.login(loginRequestDto);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        VelogAuthenticationToken authenticationToken = user.toAuthentication();

        Duration duration = jwtUtil.getDurationLeft(user.getAccessToken());
        userRedisUtil.set(user, duration);

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
