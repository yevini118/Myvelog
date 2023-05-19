package com.yevini.myvelog.velog;

import com.yevini.myvelog.response.Post;
import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stat;
import com.yevini.myvelog.response.UserTags;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
public class StatService {

    public MyvelogStats getStat(UserTags userTags, Posts posts, List<Stat> stats, MyvelogStats history) {

        int totalVisits = getTotalVisits(stats);
        int totalLikes = getTotalLikes(posts.getPosts());
        List<PostStat> topPosts = getPostsStats(posts.getPosts(), stats);

        return MyvelogStats.builder()
                .totalPosts(userTags.getTotalPostsCount())
                .totalVisits(totalVisits)
                .totalLikes(totalLikes)
                .topPosts(topPosts)
                .dateTime(LocalDateTime.now())
                .build();
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

            postStats.add(new PostStat(posts.get(i).getTitle(), posts.get(i).getLikes(), stats.get(i).getTotal()));
        }

        postStats.sort(comparing(PostStat::getVisits).reversed());

        int num=1;
        for (PostStat postStat : postStats) {
            postStat.setNum(num++);
        }

        return postStats;
    }

    private LocalDateTime getHistoryDateTime(MyvelogStats history) {

        if (history != null) {
            return history.getDateTime();
        }
        return LocalDateTime.now();
    }

    private int getVisitUp(MyvelogStats history, int totalVisits) {

        if (history != null) {
            return totalVisits - history.getTotalVisits();
        }
        return 0;
    }
}
