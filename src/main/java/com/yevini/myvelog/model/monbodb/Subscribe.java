package com.yevini.myvelog.model.monbodb;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "subscribe")
public class Subscribe {

    @Id
    private String id;
    private String subscriber;
    private String username;

    public Subscribe(String subscriber, String username) {
        this.subscriber = subscriber;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Subscribe{" +
                "id='" + id + '\'' +
                ", subscriber='" + subscriber + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
