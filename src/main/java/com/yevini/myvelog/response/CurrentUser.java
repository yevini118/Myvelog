package com.yevini.myvelog.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonDeserialize(using = CurrentUserDeserializer.class)
public class CurrentUser {

    private String username;
    private String thumbnail;
    private String displayName;
}
