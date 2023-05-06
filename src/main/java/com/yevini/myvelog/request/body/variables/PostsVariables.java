package com.yevini.myvelog.request.body.variables;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostsVariables implements Variables {

    private String username;
    private String tag;
    private int limit;
}
