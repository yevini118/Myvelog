package com.yevini.myvelog.web.dto;

import com.yevini.myvelog.model.response.Post;
import com.yevini.myvelog.model.response.UserProfile;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
public class FeedResponseDto {

    private String releasedAt;
    UserProfile userProfile;

    Post post;

    public FeedResponseDto(UserProfile userProfile, Post post) {
        this.releasedAt = post.getReleasedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        this.userProfile = userProfile;
        this.post = post;
    }
}
