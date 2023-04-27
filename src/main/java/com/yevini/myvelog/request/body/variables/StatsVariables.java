package com.yevini.myvelog.request.body.variables;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StatsVariables implements Variables{

    @JsonProperty("post_id")
    private String postId;
}
