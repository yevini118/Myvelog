package com.yevini.myvelog.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Tags {

    private String id;
    private String name;
    @JsonProperty("posts_count")
    private int postsCount;
}
