package com.yevini.myvelog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    public Duration getDurationLeft(String accessToken) {

        Date expiresAt = getExpiresAt(accessToken);
        Date now = new Date();

        return Duration.between(now.toInstant(), expiresAt.toInstant());
    }

    private Date getExpiresAt(String accessToken) {

        DecodedJWT decodedJWT = JWT.decode(accessToken);
        return decodedJWT.getExpiresAt();
    }
}
