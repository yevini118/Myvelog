package com.yevini.myvelog.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserTagsDeserializer extends JsonDeserializer<UserTags> {

    @Override
    public UserTags deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode node = p.getCodec().readTree(p);
        JsonNode dataNode = node.get("data");
        JsonNode userTagsNode = dataNode.get("userTags");
        JsonNode tagsNode = userTagsNode.get("tags");

        int posts_count = userTagsNode.get("posts_count").asInt();
        List<Tags> tags = Arrays.stream(objectMapper.treeToValue(tagsNode, Tags[].class)).toList();

        return new UserTags(posts_count, tags);
    }
}
