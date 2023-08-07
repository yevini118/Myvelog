package com.yevini.myvelog.web.dto;

import com.yevini.myvelog.model.response.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SubscribeResponseDto {

    private String id;

    private UserProfile userProfile;
}
