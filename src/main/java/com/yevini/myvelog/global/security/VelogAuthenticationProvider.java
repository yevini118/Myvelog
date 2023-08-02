package com.yevini.myvelog.global.security;

import com.yevini.myvelog.model.velog.User;
import com.yevini.myvelog.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VelogAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = userService.loadUserByUsername(authentication.getPrincipal().toString());

//        if (authentication.getCredentials() != user.getPassword()) {
//            throw new BadCredentialsException("Authentication failed: accessToken does not match");
//        }

        //예외처리
        return new VelogAuthenticationToken(userDetails.getAuthorities(), userDetails, userDetails.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return VelogAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
