package com.yevini.myvelog.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CurrentUser {

    private final String username;
    private final String thumbnail;
    private final String displayName;

    @JsonCreator
    public CurrentUser(@JsonProperty("username") String username, @JsonProperty("profile") JsonNode profileNode) {

        this.username = username;
        this.thumbnail = profileNode.get("thumbnail").asText();
        this.displayName = profileNode.get("display_name").asText();
    }
}
