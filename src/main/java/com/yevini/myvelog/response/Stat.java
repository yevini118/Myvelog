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
public class Stat {

    private String id;
    private int total;
    private List<CountByDay> countByDays;

    public Stat(int total, List<CountByDay> countByDays) {
        this.total = total;
        this.countByDays = countByDays;
    }

    public void setId(String id) {
        this.id = id;
    }
}
