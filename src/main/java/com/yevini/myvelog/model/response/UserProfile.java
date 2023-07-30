package com.yevini.myvelog.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class UserProfile {

    private final String username;
    private final String displayName;
    private final String thumbnail;

    @JsonCreator
    public UserProfile(@JsonProperty("data") JsonNode node) throws JsonProcessingException {

        JsonNode userNode = node.findValue("user");

        this.username = userNode.get("username").asText();
        this.displayName = userNode.get("profile").get("display_name").asText();
        this.thumbnail = userNode.get("profile").get("thumbnail").asText();
    }
}
