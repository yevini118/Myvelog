package com.yevini.myvelog.web.dto.request.variables;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostsVariables implements Variables {

    private String username;
    private String tag;
    private int limit;
}
