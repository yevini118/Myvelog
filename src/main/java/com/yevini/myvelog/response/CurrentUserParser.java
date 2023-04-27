package com.yevini.myvelog.response;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class CurrentUserParser extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode node = p.getCodec().readTree(p);
        JsonNode profileNode = node.get("profile");

        String username = node.get("username").asText();
        String thumbnail = profileNode.get("thumbnail").asText();
        String displayName = profileNode.get("display_name").asText();

        return new User(username, thumbnail, displayName);
    }
}
