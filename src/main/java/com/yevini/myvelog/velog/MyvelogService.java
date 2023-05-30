package com.yevini.myvelog.velog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.crawling.SeleniumService;
import com.yevini.myvelog.redis.JwtService;
import com.yevini.myvelog.redis.StatsRedisService;
import com.yevini.myvelog.redis.UserRedisService;
import com.yevini.myvelog.request.WebClientService;
import com.yevini.myvelog.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@RequiredArgsConstructor
@Service
public class MyvelogService {

    private final JwtService jwtService;
    private final UserRedisService userRedisService;
    private final SeleniumService seleniumService;
    private final StatService statService;
    private final WebClientService webClientService;
    private final StatsRedisService statsRedisService;

    public String login() throws JsonProcessingException {

        User user = seleniumService.process();

        Duration duration = jwtService.getDurationLeft(user.getAccessToken());
        userRedisService.set(user, duration);

        return user.getUsername();
    }

    public void main(String username, Model model) throws InterruptedException, JsonProcessingException {

        User user = userRedisService.get(username);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        MyvelogStats statsHistory = statsRedisService.get(username);

        UserTags userTags = webClientService.getUserTags(username);
        Posts posts = webClientService.getPosts(username, userTags.getTotalPostsCount());

        List<Stat> stats = webClientService.getStats(posts.getPosts(), user.getAccessToken());

        MyvelogStats myvelogStats = statService.getStat(userTags, posts, stats);
        statsRedisService.set(username, myvelogStats);

        int visitsUp = 0;
        int likesUp = 0;
        int postsUp = 0;
        LocalDateTime dateTime = LocalDateTime.now();
        if (statsHistory != null) {
            visitsUp = myvelogStats.getTotalVisits() - statsHistory.getTotalVisits();
            dateTime = statsHistory.getDateTime();
            likesUp = myvelogStats.getTotalLikes() - statsHistory.getTotalLikes();
            postsUp = myvelogStats.getTotalPosts() -statsHistory.getTotalPosts();
        }

        model.addAttribute("user", user);
        model.addAttribute("stats", myvelogStats);
        model.addAttribute("visitsUp", visitsUp);
        model.addAttribute("isLikesUp", likesUp >= 0);
        model.addAttribute("likesUp", likesUp);
        model.addAttribute("postsUp", postsUp);
        model.addAttribute("datetime", dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        model.addAttribute("new", getNewVisits(statsHistory.getTopPosts(), myvelogStats.getTopPosts()));

    }

    public void post(String username, Model model) {

        User user = userRedisService.get(username);
        if (user == null) {
            throw new IllegalArgumentException();
        }

        MyvelogStats myvelogStats = statsRedisService.get(username);

        model.addAttribute("user", user);
        model.addAttribute("stats", myvelogStats);
    }

    public void day(String username, LocalDate date, Model model) {

        User user = userRedisService.get(username);
        if (user == null) {
            throw new IllegalArgumentException();
        }

        if(date == null) {
            date = LocalDate.now();
        }

        MyvelogStats myvelogStats = statsRedisService.get(username);

        model.addAttribute("user", user);
        model.addAttribute("dateB", date.minusDays(1));
        model.addAttribute("dateA", date.plusDays(1));
        model.addAttribute("isDateA", date.isEqual(LocalDate.now()));
        model.addAttribute("date", date);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("stats", getPostStatByDate(date ,myvelogStats.getTopPosts()));
    }

    public void logout(String username) {

        userRedisService.delete(username);
    }

    private List<PostStat> getPostStatByDate(LocalDate date, List<PostStat> postStats) {

        List<PostStat> newPostStats = new ArrayList<>();

        for (PostStat postStat : postStats) {
            CountByDay countByDay1 = postStat.getCountByDays().stream().filter(countByDay -> countByDay.getDay().equals(date)).findFirst().orElse(null);

            if (countByDay1 != null) {
                newPostStats.add(new PostStat(postStat, countByDay1.getCount()));
            }
        }

        newPostStats.sort(comparing(PostStat::getVisits).reversed());

        int num=1;
        for (PostStat postStat : newPostStats) {
            postStat.setNum(num++);
        }

        return newPostStats;
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

        System.out.println(newList.size());
        for (PostStat postStat : newList) {
            System.out.println(postStat.toString());
        }
        return newList;
    }


}
