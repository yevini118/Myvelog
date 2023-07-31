package com.yevini.myvelog.model.velog;

import com.yevini.myvelog.global.security.VelogAuthenticationFilter;
import com.yevini.myvelog.global.security.VelogAuthenticationToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        return new VelogAuthenticationToken(accessToken);
    }
}
