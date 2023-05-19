package com.yevini.myvelog.velog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostStat {

    private int num;
    private String title;
    private int likes;
    private int visits;

    @Builder
    public PostStat(String title, int likes, int visits) {
        this.title = title;
        this.likes = likes;
        this.visits = visits;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
