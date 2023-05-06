package com.yevini.myvelog.velog;

import com.yevini.myvelog.response.Post;
import com.yevini.myvelog.response.Posts;
import com.yevini.myvelog.response.Stat;
import com.yevini.myvelog.response.UserTags;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparingInt;

@Service
public class StatService {

    public MyvelogStats getStat(UserTags userTags, Posts posts, List<Stat> stats) {

        int totalVisits = getTotalVisits(stats);
        int totalLikes = getTotalLikes(posts.getPosts());
        List<PostStats> topPosts = getTopPosts(posts, stats);

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

    private List<PostStats> getTopPosts(Posts posts, List<Stat> stats) {

        stats.sort(comparingInt(Stat::getTotal).reversed());

        List<PostStats> postStats = new ArrayList<>();
        int postsSize = posts.getPosts().size();

        for(int i=0; i<10; i++) {
            int finalI = i;

            if (i == postsSize-1) {
                break;
            }
            Optional<Post> topPost = posts.getPosts().stream().filter(post -> post.getId().equals(stats.get(finalI).getId())).findAny();
            postStats.add(new PostStats(i+1, topPost.get().getTitle(), topPost.get().getLikes(), stats.get(i).getTotal()));
        }
        return postStats;
    }
}
