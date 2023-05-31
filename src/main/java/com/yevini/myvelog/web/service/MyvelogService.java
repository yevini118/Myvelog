package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.util.redis.StatsRedisUtil;
import com.yevini.myvelog.velog.MyvelogStats;
import com.yevini.myvelog.velog.News;
import com.yevini.myvelog.velog.PostStat;
import com.yevini.myvelog.web.dto.DayResponseDto;
import com.yevini.myvelog.web.dto.MainResponseDto;
import com.yevini.myvelog.web.dto.PostResponseDto;
import com.yevini.myvelog.web.dto.response.Posts;
import com.yevini.myvelog.web.dto.response.Stat;
import com.yevini.myvelog.web.dto.response.UserTags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyvelogService {

    private final StatService statService;
    private final WebClientService webClientService;
    private final StatsRedisUtil statsRedisUtil;

    public MainResponseDto main(String username, String accessToken) throws InterruptedException, JsonProcessingException {

        MyvelogStats statsHistory = statsRedisUtil.get(username);

        UserTags userTags = webClientService.getUserTags(username);
        Posts posts = webClientService.getPosts(username, userTags.getTotalPostsCount());
        List<Stat> stats = webClientService.getStats(posts.getPosts(), accessToken);

        MyvelogStats myvelogStats = statService.getMyVelogStats(userTags, posts, stats);
        statsRedisUtil.set(username, myvelogStats);

        News news = statService.getNews(statsHistory, myvelogStats);

        return new MainResponseDto(myvelogStats, news);
    }

    public PostResponseDto post(String username) {

        return new PostResponseDto(statsRedisUtil.get(username).getPostStats());
    }

    public DayResponseDto day(String username, LocalDate date) {

        if(date == null) {
            date = LocalDate.now();
        }

        MyvelogStats myvelogStats = statsRedisUtil.get(username);
        List<PostStat> postStatByDate = statService.getPostStatByDate(date, myvelogStats.getPostStats());

        return new DayResponseDto(date, postStatByDate);
    }
}
