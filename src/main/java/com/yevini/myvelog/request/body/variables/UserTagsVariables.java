package com.yevini.myvelog.request.body.variables;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserTagsVariables implements Variables {

    private String username;
}
