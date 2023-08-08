package com.yevini.myvelog.global.security;

import com.yevini.myvelog.web.service.SeleniumService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class VelogLogoutSuccessHandler implements LogoutSuccessHandler {

    private final SeleniumService seleniumService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        seleniumService.logout();
        response.sendRedirect("/");
    }
}
