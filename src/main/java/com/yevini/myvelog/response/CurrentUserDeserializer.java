package com.yevini.myvelog.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class CurrentUserDeserializer extends JsonDeserializer<CurrentUser> {

    @Override
    public CurrentUser deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode node = p.getCodec().readTree(p);
        JsonNode profileNode = node.get("profile");

        String username = node.get("username").asText();
        String thumbnail = profileNode.get("thumbnail").asText();
        String displayName = profileNode.get("display_name").asText();

        return new CurrentUser(username, thumbnail, displayName);
    }
}
