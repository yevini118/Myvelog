package com.yevini.myvelog.velog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@Getter
public class MyvelogStats {


    private int totalPosts;
    private int totalVisits;
    private int totalLikes;
    private List<PostStat> topPosts;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @Builder
    public MyvelogStats(int totalPosts, int totalVisits, int totalLikes, List<PostStat> topPosts, LocalDateTime dateTime) {
        this.totalPosts = totalPosts;
        this.totalVisits = totalVisits;
        this.totalLikes = totalLikes;
        this.topPosts = topPosts;
        this.dateTime = dateTime;
    }
}
