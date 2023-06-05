package com.yevini.myvelog.model.velog;

import com.yevini.myvelog.model.response.CurrentUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String thumbnail;
    private String displayName;
    private String accessToken;

    public User(CurrentUser currentUser, String accessToken){
        this.username = currentUser.getUsername();
        this.thumbnail = currentUser.getThumbnail();
        this.displayName = currentUser.getDisplayName();
        this.accessToken = accessToken;
    }
}
