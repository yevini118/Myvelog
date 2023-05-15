package com.yevini.myvelog.velog;

import com.yevini.myvelog.response.Post;
import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stat;
import com.yevini.myvelog.response.UserTags;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
public class StatService {

    public MyvelogStats getStat(UserTags userTags, Posts posts, List<Stat> stats) {

        int totalVisits = getTotalVisits(stats);
        int totalLikes = getTotalLikes(posts.getPosts());
        List<PostStat> topPosts = getPostsStats(posts.getPosts(), stats);

        return MyvelogStats.builder()
                .totalPosts(userTags.getTotalPostsCount())
                .totalVisits(totalVisits)
                .totalLikes(totalLikes)
                .topPosts(topPosts)
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

        return postStats;
    }
}
