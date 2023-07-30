package com.yevini.myvelog.global.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class VelogAuthenticationToken extends AbstractAuthenticationToken {

    private final String velogAccessToken;

    public VelogAuthenticationToken(String velogAccessToken) {
        super(null);
        this.velogAccessToken = velogAccessToken;
        setAuthenticated(false);
    }

    public VelogAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String velogAccessToken) {
        super(authorities);
        this.velogAccessToken = velogAccessToken;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return velogAccessToken;
    }
}
