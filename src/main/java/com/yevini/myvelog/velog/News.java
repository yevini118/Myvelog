package com.yevini.myvelog.velog;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Getter
public class News {

    private final String historyDateTime;
    private final int visitsUp;
    private final int likesUp;
    private final int postsUp;
    private final List<PostStat> postStats;

    public News() {
        this.historyDateTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        this.visitsUp = 0;
        this.likesUp = 0;
        this.postsUp = 0;
        this.postStats = new ArrayList<>();
    }

    @Builder
    public News(LocalDateTime historyDateTime, int visitsUp, int likesUp, int postsUp, List<PostStat> postStats) {
        this.historyDateTime = historyDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        this.visitsUp = visitsUp;
        this.likesUp = likesUp;
        this.postsUp = postsUp;
        this.postStats = postStats;
    }
}
