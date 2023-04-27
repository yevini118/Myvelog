package com.yevini.myvelog.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PostsDeserializer extends JsonDeserializer<Posts> {

    @Override
    public Posts deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode node = p.getCodec().readTree(p);
        JsonNode dataNode = node.get("data");
        JsonNode postsNode = dataNode.get("posts");

        List<Post> posts = Arrays.stream(objectMapper.treeToValue(postsNode, Post[].class)).toList();

        return new Posts(posts);
    }
}
