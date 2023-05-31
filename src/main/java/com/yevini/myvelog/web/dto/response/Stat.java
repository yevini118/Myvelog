package com.yevini.myvelog.web.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class Stat {

    private String id;
    private final int total;
    private final List<CountByDay> countByDays;

    public void setId(String id) {
        this.id = id;
    }

    @JsonCreator
    public Stat(@JsonProperty("data")JsonNode node) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode dayNode = node.findValue("count_by_day");

        this.countByDays = Arrays.stream(objectMapper.treeToValue(dayNode, CountByDay[].class)).toList();
        this.total = node.findValue("total").asInt();
    }
}
