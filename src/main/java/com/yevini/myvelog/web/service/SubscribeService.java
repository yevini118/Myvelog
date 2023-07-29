package com.yevini.myvelog.web.service;

import com.yevini.myvelog.model.monbodb.Subscribe;
import com.yevini.myvelog.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public void save(String username, String subscribe){
        subscribeRepository.save(new Subscribe(username, subscribe));
    }
}
