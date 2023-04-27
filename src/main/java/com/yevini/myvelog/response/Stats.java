package com.yevini.myvelog.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonDeserialize(using = StatsDeserializer.class)
public class Stats {

    private int total;
    private List<CountByDay> countByDays;
}
