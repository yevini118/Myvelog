package com.yevini.myvelog.request.body.variables;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatsVariables implements Variables{

    @JsonProperty("post_id")
    private String postId;
}
