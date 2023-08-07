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

    public boolean save(String username, String subscribe){
        boolean userProfileExists = webClientService.isUserProfileExists(subscribe);

        if (userProfileExists) {
            subscribeRepository.save(new Subscribe(username, subscribe));
            return true;
        }
        return false;
    }

    public List<UserProfile> getSubscribes(String username) {

        List<Subscribe> subscribes = subscribeRepository.findBySubscriber(username);
        return webClientService.getUserProfile(subscribes);
    }
}
