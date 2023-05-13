package com.yevini.myvelog.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
public class Posts {

    List<Post> posts;

    @JsonCreator
    public Posts(@JsonProperty("data") JsonNode node) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode postsNode = node.findValue("posts");

        this.posts =  Arrays.stream(objectMapper.treeToValue(postsNode, Post[].class)).toList();
    }
}
