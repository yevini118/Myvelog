package com.yevini.myvelog.velog;

import com.yevini.myvelog.web.dto.response.CountByDay;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PostStat {

    private int num;
    private String id;
    private String title;
    private int likes;
    private int visits;
    private List<CountByDay> countByDays;

    @Builder
    public PostStat(String id, String title, int likes, int visits, List<CountByDay> countByDays) {
        this.id = id;
        this.title = title;
        this.likes = likes;
        this.visits = visits;
        this.countByDays = countByDays;
    }

    public PostStat(PostStat postStat, int visits) {
        this.id = postStat.getId();
        this.title = postStat.getTitle();
        this.visits = visits;
    }

    public PostStat(PostStat postStat, int visitsUp, int likesUp) {
        this.id = postStat.getId();
        this.title = postStat.getTitle();
        this.visits = visitsUp;
        this.likes = likesUp;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
