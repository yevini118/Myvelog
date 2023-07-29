package com.yevini.myvelog.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class SubscribeController {

    @GetMapping("/feed/{username}")
    public String feed(@PathVariable String username) {
        return "feed";
    }

    @PostMapping("/subscribe/{username}")
    public String add(@PathVariable String username, )

    @GetMapping("/subscribe/{username}")
    public String getSubscribes(@PathVariable String username) {
        return "subscribe";
    }
}
