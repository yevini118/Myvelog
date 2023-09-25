package com.yevini.myvelog.global.security;

import com.yevini.myvelog.global.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Duration;

@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String defaultSuccessUrl;
    private final JwtUtil jwtUtil;

    public CustomAuthenticationSuccessHandler(String defaultSuccessUrl, JwtUtil jwtUtil) {
        this.defaultSuccessUrl = defaultSuccessUrl;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = customUserDetails.getUsername();

        Duration duration = jwtUtil.getDurationLeft(customUserDetails.getUser().getAccessToken());
        request.getSession().setMaxInactiveInterval((int) duration.toSeconds());

        log.info("[Login] {}",username);
        response.sendRedirect(defaultSuccessUrl);
    }
}
