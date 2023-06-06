package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.global.util.redis.MyVelogStatsRedisUtil;
import com.yevini.myvelog.model.velog.MyvelogStats;
import com.yevini.myvelog.model.velog.News;
import com.yevini.myvelog.model.velog.PostStat;
import com.yevini.myvelog.web.dto.DayResponseDto;
import com.yevini.myvelog.web.dto.MainResponseDto;
import com.yevini.myvelog.web.dto.PostResponseDto;
import com.yevini.myvelog.model.response.Posts;
import com.yevini.myvelog.model.response.Stat;
import com.yevini.myvelog.model.response.UserTags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyvelogService {

    private final MyVelogStatsService myVelogStatsService;
    private final WebClientService webClientService;
    private final MyVelogStatsRedisUtil myVelogStatsRedisUtil;

    public MainResponseDto main(String username, String accessToken) throws InterruptedException, JsonProcessingException {

        UserTags userTags = webClientService.getUserTags(username);
        Posts posts = webClientService.getPosts(username, userTags.getTotalPostsCount());
        List<Stat> stats = webClientService.getStats(posts.getPosts(), accessToken);

        MyvelogStats statsHistory = myVelogStatsRedisUtil.get(username);

        MyvelogStats myvelogStats = myVelogStatsService.getMyVelogStats(userTags, posts, stats);
        myVelogStatsRedisUtil.set(username, myvelogStats);

        News news = myVelogStatsService.getNews(statsHistory, myvelogStats);

        return new MainResponseDto(myvelogStats, news);
    }

    public PostResponseDto post(String username) {

        return new PostResponseDto(myVelogStatsRedisUtil.get(username).getPostStats());
    }

    public DayResponseDto day(String username, LocalDate date) {

        if(date == null) {
            date = LocalDate.now();
        }

        MyvelogStats myvelogStats = myVelogStatsRedisUtil.get(username);
        List<PostStat> postStatByDate = myVelogStatsService.getPostStatsByDate(date, myvelogStats.getPostStats());

        return new DayResponseDto(date, postStatByDate);
    }
}
