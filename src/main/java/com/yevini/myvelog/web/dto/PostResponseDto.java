package com.yevini.myvelog.web.dto;

import com.yevini.myvelog.velog.PostStat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponseDto {

    private final List<PostStat> postStats;
}
