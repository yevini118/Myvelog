package com.yevini.myvelog.model.velog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yevini.myvelog.global.security.VelogAuthenticationToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{

    private String username;
    private String thumbnail;
    private String displayName;
    private String accessToken;

    public VelogAuthenticationToken toAuthentication() {

        return new VelogAuthenticationToken(username, accessToken);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", displayName='" + displayName + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
