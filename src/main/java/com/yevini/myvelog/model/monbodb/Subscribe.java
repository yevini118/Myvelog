package com.yevini.myvelog.model.monbodb;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@CompoundIndex(def = "{'subscribe' : 1, 'username' : 1}", unique = true)
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
