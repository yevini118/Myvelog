package com.yevini.myvelog.web.service;

import com.yevini.myvelog.model.monbodb.Subscribe;
import com.yevini.myvelog.model.response.Posts;
import com.yevini.myvelog.repository.SubscribeRepository;
import com.yevini.myvelog.web.dto.FeedResponseDto;
import com.yevini.myvelog.web.dto.SubscribeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final WebClientService webClientService;

    public List<FeedResponseDto> feed(String username) {

        List<FeedResponseDto> feeds = new ArrayList<>();
        List<SubscribeResponseDto> subscribes = getSubscribes(username);
        subscribes.forEach(s -> {
            Posts posts = webClientService.getPosts(s.getUserProfile().getUsername(), 5);
            posts.getPosts().forEach(p ->{
                feeds.add(new FeedResponseDto(s.getUserProfile(), p));
            });
        });

        feeds.sort(Comparator.comparing((FeedResponseDto feedResponseDto) -> feedResponseDto.getReleasedAt()).reversed());

        return feeds;
    }

    public boolean save(String username, String subscribe){
        boolean userProfileExists = webClientService.isUserProfileExists(subscribe);

        if (userProfileExists) {
            subscribeRepository.save(new Subscribe(username, subscribe));
            return true;
        }
        return false;
    }

    public void delete(String id, String username) {

        subscribeRepository.deleteById(id);
    }

    public List<SubscribeResponseDto> getSubscribes(String username) {

        List<Subscribe> subscribes = subscribeRepository.findBySubscriber(username);
        return webClientService.getUserProfile(subscribes);
    }
}
