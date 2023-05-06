package com.yevini.myvelog.velog;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MyvelogStats {

    private int totalPosts;
    private int totalVisits;
    private int totalLikes;
    private List<PostStats> topPosts;
}
