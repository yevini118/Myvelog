package com.yevini.myvelog.web.service;

import com.yevini.myvelog.velog.MyvelogStats;
import com.yevini.myvelog.velog.News;
import com.yevini.myvelog.velog.PostStat;
import com.yevini.myvelog.web.dto.response.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
public class StatService {

    public MyvelogStats getMyVelogStats(UserTags userTags, Posts posts, List<Stat> stats) {

        int totalVisits = getTotalVisits(stats);
        int totalLikes = getTotalLikes(posts.getPosts());
        List<PostStat> postStats = getPostsStats(posts.getPosts(), stats);

        return MyvelogStats.builder()
                .totalPosts(userTags.getTotalPostsCount())
                .totalVisits(totalVisits)
                .totalLikes(totalLikes)
                .postStats(postStats)
                .dateTime(LocalDateTime.now())
                .build();
    }

    public News getNews(MyvelogStats statsHistory, MyvelogStats myvelogStats) {

        if (statsHistory == null) {
            return new News();
        }

        return News.builder()
                .historyDateTime(statsHistory.getDateTime())
                .visitsUp(myvelogStats.getTotalVisits() - statsHistory.getTotalVisits())
                .likesUp(myvelogStats.getTotalLikes() - statsHistory.getTotalLikes())
                .postsUp(myvelogStats.getTotalPosts() - statsHistory.getTotalPosts())
                .postStats(getNewVisits(statsHistory.getPostStats(), myvelogStats.getPostStats()))
                .build();
    }

    public List<PostStat> getPostStatByDate(LocalDate date, List<PostStat> postStats) {

        List<PostStat> newPostStats = new ArrayList<>();

        for (PostStat postStat : postStats) {
            CountByDay countByDay1 = postStat.getCountByDays().stream().filter(countByDay -> countByDay.getDay().equals(date)).findFirst().orElse(null);

            if (countByDay1 != null) {
                newPostStats.add(new PostStat(postStat, countByDay1.getCount()));
            }
        }

        newPostStats.sort(comparing(PostStat::getVisits).reversed());

        setNums(newPostStats);

        return newPostStats;
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

        System.out.println(stats.size() == posts.size());

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
        setNums(postStats);

        return postStats;
    }

    private List<PostStat> getNewVisits(List<PostStat> postStatsHistory, List<PostStat> postStats) {

        List<PostStat> newList = new ArrayList<>();

        for (PostStat postStat : postStats) {
            postStatsHistory.stream()
                    .filter(history -> history.getId().equals(postStat.getId()) && (history.getVisits() != postStat.getVisits() || history.getLikes() != postStat.getLikes()))
                    .findFirst()
                    .map(any -> new PostStat(any, postStat.getVisits() - any.getVisits(), postStat.getLikes() - any.getLikes()))
                    .ifPresent(newList::add);
        }

        newList.sort(comparing(PostStat::getVisits).reversed());

        setNums(newList);

        return newList;
    }

    private void setNums(List<PostStat> postStats) {
        int num=1;
        for (PostStat postStat : postStats) {
            postStat.setNum(num++);
        }
    }
}
