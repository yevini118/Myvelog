package com.yevini.myvelog.global.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class VelogAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;
    private String velogAccessToken;

    public VelogAuthenticationToken(Object principal, String velogAccessToken) {
        super(null);
        this.principal = principal;
        this.velogAccessToken = velogAccessToken;
        setAuthenticated(false);
    }

    public VelogAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, String velogAccessToken) {
        super(authorities);
        this.principal = principal;
        this.velogAccessToken = velogAccessToken;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return velogAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
