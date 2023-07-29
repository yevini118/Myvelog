package com.yevini.myvelog.web.controller;

import com.yevini.myvelog.repository.SubscribeRepository;
import com.yevini.myvelog.web.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SubscribeController {

    private final SubscribeService subscribeService;

    @GetMapping("/feed/{username}")
    public String feed(@PathVariable String username) {
        return "feed";
    }

    @PostMapping("/subscribe/{username}")
    public String add(@PathVariable String username, @RequestParam String subscribe){
        subscribeService.save(username, subscribe);
        System.out.println(subscribe);
        return "subscribe";
    }

    @GetMapping("/subscribe/{username}")
    public String getSubscribes(@PathVariable String username) {
        return "subscribe";
    }
}
