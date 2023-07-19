package com.yevini.myvelog.model.velog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yevini.myvelog.model.response.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;


@NoArgsConstructor
@Getter
public class MyvelogStats {

    private int totalPosts;
    private int totalVisits;
    private int totalLikes;
    private List<PostStat> postStats;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @Builder
    public MyvelogStats(int totalPosts, int totalVisits, int totalLikes, List<PostStat> postStats, LocalDateTime dateTime) {
        this.totalPosts = totalPosts;
        this.totalVisits = totalVisits;
        this.totalLikes = totalLikes;
        this.postStats = postStats;
        this.dateTime = dateTime;
    }

    public MyvelogStats(UserTags userTags, Posts posts, List<Stat> stats) {

        int totalVisits = getTotalVisits(stats);
        int totalLikes = getTotalLikes(posts.getPosts());
        List<PostStat> postStats = getPostsStats(posts.getPosts(), stats);

        this.totalPosts = userTags.getTotalPostsCount();
        this.totalVisits = totalVisits;
        this.totalLikes = totalLikes;
        this.postStats = postStats;
        this.dateTime = LocalDateTime.now();
    }

    private int getTotalVisits(List<Stat> stats) {

        return stats.stream().mapToInt(Stat::getTotal).sum();
    }

    private int getTotalLikes(List<Post> posts) {

        return posts.stream().mapToInt(Post::getLikes).sum();
    }

    private List<PostStat> getPostsStats(List<Post> posts, List<Stat> stats) {

        stats.sort(comparing(Stat::getId));
        posts.sort(comparing(Post::getId));

        List<PostStat> postStats = new ArrayList<>();

        int postsSize = posts.size();
        for(int i=0; i<postsSize; i++) {
            postStats.add(PostStat.builder()
                    .id(posts.get(i).getId())
                    .title(posts.get(i).getTitle())
                    .likes(posts.get(i).getLikes())
                    .visits(stats.get(i).getTotal())
                    .countByDays(stats.get(i).getCountByDays())
                    .build());
        }

        postStats.sort(comparing(PostStat::getVisits).reversed());

        return postStats;
    }

    public List<PostStat> getPostStatsByDate(LocalDate date, List<PostStat> postStats) {

        List<PostStat> newPostStats = new ArrayList<>();

        for (PostStat postStat : postStats) {
            CountByDay countByDay1 = postStat.getCountByDays().stream().filter(countByDay -> countByDay.getDay().equals(date)).findFirst().orElse(null);

            if (countByDay1 != null) {
                newPostStats.add(new PostStat(postStat, countByDay1.getCount()));
            }
        }

        newPostStats.sort(comparing(PostStat::getVisits).reversed());

        return newPostStats;
    }
}
