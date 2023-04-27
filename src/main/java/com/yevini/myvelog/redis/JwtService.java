package com.yevini.myvelog.redis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class JwtService {

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
