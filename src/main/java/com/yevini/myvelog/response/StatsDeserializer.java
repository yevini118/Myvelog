package com.yevini.myvelog.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StatsDeserializer extends JsonDeserializer<Stat> {

    @Override
    public Stat deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode node = p.getCodec().readTree(p);
        JsonNode dataNode = node.get("data");
        JsonNode statsNode = dataNode.get("getStats");
        JsonNode dayNode = statsNode.get("count_by_day");

        int total = statsNode.get("total").asInt();
        List<CountByDay> countByDays = Arrays.stream(objectMapper.treeToValue(dayNode, CountByDay[].class)).toList();

        return new Stat(total, countByDays);
    }
}
