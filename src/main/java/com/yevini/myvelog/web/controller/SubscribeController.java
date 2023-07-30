package com.yevini.myvelog.web.controller;

import com.yevini.myvelog.model.monbodb.Subscribe;
import com.yevini.myvelog.model.response.UserProfile;
import com.yevini.myvelog.repository.SubscribeRepository;
import com.yevini.myvelog.web.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        return "redirect:/subscribe/" + username;
    }

    @GetMapping("/subscribe/{username}")
    public String getSubscribes(@PathVariable String username, Model model) {

        List<UserProfile> userProfiles = subscribeService.getSubscribes(username);
        model.addAttribute("userProfiles", userProfiles);

        return "subscribe";
    }
}
