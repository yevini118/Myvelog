package com.yevini.myvelog.web.service;

import com.yevini.myvelog.model.monbodb.Subscribe;
import com.yevini.myvelog.model.response.UserProfile;
import com.yevini.myvelog.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final WebClientService webClientService;

    public void save(String username, String subscribe){
        boolean user = webClientService.isUserProfileExists(subscribe);
        System.out.println(user);
        subscribeRepository.save(new Subscribe(username, subscribe));
    }

    public List<UserProfile> getSubscribes(String username) {

        List<Subscribe> subscribes = subscribeRepository.findBySubscriber(username);
        return webClientService.getUserProfile(subscribes);
    }
}
