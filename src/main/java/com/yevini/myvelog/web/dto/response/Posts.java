package com.yevini.myvelog.web.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Posts {

    private final List<Post> posts;
    private LocalDateTime dateTime;

    @JsonCreator
    public Posts(@JsonProperty("data") JsonNode node) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode postsNode = node.findValue("posts");

        this.posts =  Stream.of(objectMapper.treeToValue(postsNode, Post[].class)).collect(Collectors.toList());
    }
}
