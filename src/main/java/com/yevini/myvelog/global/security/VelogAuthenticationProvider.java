package com.yevini.myvelog.global.security;

import com.yevini.myvelog.global.util.redis.UserRedisUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class VelogAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("user");
        String accessToken = authentication.getPrincipal().toString();

        return new VelogAuthenticationToken(authorities, accessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return VelogAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
