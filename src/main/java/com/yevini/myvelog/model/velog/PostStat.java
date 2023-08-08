package com.yevini.myvelog.model.velog;

import com.yevini.myvelog.model.response.CountByDay;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PostStat {

    private String id;
    private String title;
    private String urlSlug;
    private int likes;
    private int commentsCount;
    private int visits;
    private List<CountByDay> countByDays;

    @Builder
    public PostStat(String id, String title, String urlSlug, int likes, int commentsCount, int visits, List<CountByDay> countByDays) {
        this.id = id;
        this.title = title;
        this.urlSlug = urlSlug;
        this.likes = likes;
        this.commentsCount = commentsCount;
        this.visits = visits;
        this.countByDays = countByDays;
    }

    public PostStat(PostStat postStat, int visits) {
        this.id = postStat.getId();
        this.urlSlug = postStat.getUrlSlug();
        this.title = postStat.getTitle();
        this.visits = visits;
    }

}
