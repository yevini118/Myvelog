package com.yevini.myvelog.web.dto;

import com.yevini.myvelog.velog.PostStat;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class DayResponseDto {

    private final LocalDate dateBefore;
    private final LocalDate dateAfter;
    private final LocalDate date;
    private final LocalDate today;
    private final boolean isToday;

    private final int totalVisits;
    private final List<PostStat> postStats;

    public DayResponseDto(LocalDate date, List<PostStat> postStats) {
        this.dateBefore = date.minusDays(1);
        this.dateAfter = date.plusDays(1);
        this.date = date;
        this.today = LocalDate.now();
        this.isToday = date.isEqual(today);
        this.postStats = postStats;
        this.totalVisits = postStats.stream().mapToInt(PostStat::getVisits).sum();
    }
}
