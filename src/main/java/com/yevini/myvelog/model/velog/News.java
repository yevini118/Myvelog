package com.yevini.myvelog.model.velog;

import com.yevini.myvelog.web.dto.NewPostStat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Getter
public class News {

    private final String historyDateTime;
    private final int visitsUp;
    private final int likesUp;
    private final int postsUp;
    private final List<NewPostStat> postStats;

    public News() {
        this.historyDateTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        this.visitsUp = 0;
        this.likesUp = 0;
        this.postsUp = 0;
        this.postStats = new ArrayList<>();
    }

    public News(MyvelogStats statsHistory, MyvelogStats myvelogStats) {

        this.historyDateTime = statsHistory.getDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        this.visitsUp = myvelogStats.getTotalVisits() - statsHistory.getTotalVisits();
        this.likesUp = myvelogStats.getTotalLikes() - statsHistory.getTotalLikes();
        this.postsUp = myvelogStats.getTotalPosts() - statsHistory.getTotalPosts();
        this.postStats = getNewPostStats(statsHistory.getPostStats(), myvelogStats.getPostStats());
    }

    private List<NewPostStat> getNewPostStats(List<PostStat> postStatsHistory, List<PostStat> postStats) {

        List<NewPostStat> newList = new ArrayList<>();

        for (PostStat postStat : postStats) {
            postStatsHistory.stream()
                    .filter(history -> history.getId().equals(postStat.getId()) && (history.getVisits() != postStat.getVisits() || history.getLikes() != postStat.getLikes()))
                    .findFirst()
                    .map(any -> new NewPostStat(any, postStat.getVisits() - any.getVisits(), postStat.getLikes() - any.getLikes()))
                    .ifPresent(newList::add);
        }

        newList.sort(comparing(NewPostStat::getVisitsUp).reversed());

        return newList;
    }
}
