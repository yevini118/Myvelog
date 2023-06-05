package com.yevini.myvelog.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post {

    private String id;
    private String title;
    private String thumbnail;
    @JsonProperty("comments_count")
    private int commentsCount;
    private List<String> tags;
    private int likes;

}
