package com.yevini.myvelog.web.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class UserTags{

    private final int totalPostsCount;
    private final List<Tags> tags;

    @JsonCreator
    public UserTags(@JsonProperty("data") JsonNode node) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode tagsNode = node.findValue("tags");

        this.totalPostsCount = node.get("userTags").get("posts_count").asInt();
        this.tags =  Arrays.stream(objectMapper.treeToValue(tagsNode, Tags[].class)).toList();
    }
}
