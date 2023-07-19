package com.yevini.myvelog.web.dto;

import com.yevini.myvelog.model.velog.PostStat;
import lombok.Getter;

@Getter
public class NewPostStat {

    private String id;
    private String title;
    private int likesUp;
    private int visitsUp;

    public NewPostStat(PostStat postStat, int visitsUp, int likesUp) {
        this.id = postStat.getId();
        this.title = postStat.getTitle();
        this.visitsUp = visitsUp;
        this.likesUp = likesUp;
    }
}
