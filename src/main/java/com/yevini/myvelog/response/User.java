package com.yevini.myvelog.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@JsonDeserialize(using = CurrentUserParser.class)
public class User {

    private String username;
    private String thumbnail;
    private String displayName;
    private String accessToken;

    public User(String username, String thumbnail, String displayName) {
        this.username = username;
        this.thumbnail = thumbnail;
        this.displayName = displayName;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
