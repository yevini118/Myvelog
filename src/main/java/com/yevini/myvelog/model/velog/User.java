package com.yevini.myvelog.model.velog;

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
}
