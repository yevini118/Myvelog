package com.yevini.myvelog.velog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.crawling.SeleniumService;
import com.yevini.myvelog.redis.JwtService;
import com.yevini.myvelog.redis.UserRedisService;
import com.yevini.myvelog.request.RestTemplateService;
import com.yevini.myvelog.request.WebClientService;
import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stat;
import com.yevini.myvelog.response.User;
import com.yevini.myvelog.response.UserTags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyvelogService {

    private final JwtService jwtService;
    private final UserRedisService userRedisService;
    private final SeleniumService seleniumService;
    private final StatService statService;
    private final WebClientService webClientService;
    private final RestTemplateService restTemplateService;

    public String login() throws JsonProcessingException {

        User user = seleniumService.process();

        Duration duration = jwtService.getDurationLeft(user.getAccessToken());
        userRedisService.set(user, duration);

        return user.getUsername();
    }

    public void main(String username, Model model) throws InterruptedException, JsonProcessingException {

        User user = userRedisService.get(username);

        UserTags userTags = webClientService.getUserTags(username);
        Posts posts = webClientService.getPosts(username, userTags.getTotalPostsCount());

        List<Stat> stats = webClientService.getStats(posts.getPosts(), user.getAccessToken());

        MyvelogStats myvelogStats = statService.getStat(userTags, posts, stats);

        model.addAttribute("user", user);
        model.addAttribute("stats", myvelogStats);
    }

    public void post(String username, Model model) {


    }

    public void logout(String username) {

        userRedisService.delete(username);
    }

}
