package com.yevini.myvelog.web.dto;

import com.yevini.myvelog.velog.MyvelogStats;
import com.yevini.myvelog.velog.News;
import lombok.Getter;

@Getter
public class MainResponseDto {

    private final MyvelogStats myvelogStats;
    private final News news;
    private final boolean isLikesUp;

    public MainResponseDto(MyvelogStats myvelogStats, News news) {
        this.myvelogStats = myvelogStats;
        this.news = news;
        this.isLikesUp = news.getLikesUp() >= 0;
    }
}
