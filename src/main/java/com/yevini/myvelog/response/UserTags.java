package com.yevini.myvelog.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonDeserialize(using = UserTagsDeserializer.class)
public class UserTags{

    private int totalPostsCount;
    private List<Tags> tags;
}
