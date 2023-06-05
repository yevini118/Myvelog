package com.yevini.myvelog.web.dto;

import com.yevini.myvelog.model.velog.MyvelogStats;
import com.yevini.myvelog.model.velog.News;
import lombok.Getter;

@Getter
public class MainResponseDto {

    private final MyvelogStats myvelogStats;
    private final News news;

    public MainResponseDto(MyvelogStats myvelogStats, News news) {
        this.myvelogStats = myvelogStats;
        this.news = news;
    }
}
