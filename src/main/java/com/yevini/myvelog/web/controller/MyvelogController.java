package com.yevini.myvelog.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.global.security.CustomUserDetails;
import com.yevini.myvelog.model.velog.User;
import com.yevini.myvelog.web.dto.MainResponseDto;
import com.yevini.myvelog.web.service.MyvelogService;
import com.yevini.myvelog.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class MyvelogController {

    private final MyvelogService myvelogService;
    private final UserService userService;

    @GetMapping("")
    public String index() {

        return "index";
    }

    @GetMapping("/main/{username}")
    public String main(@PathVariable String username, Model model, @AuthenticationPrincipal Object customUserDetails) throws InterruptedException, JsonProcessingException {

        User user = userService.getUser(username);

        MainResponseDto responseDto = myvelogService.main(username, user.getAccessToken());

        model.addAttribute("user", user);
        model.addAttribute("stats", responseDto.getMyvelogStats());
        model.addAttribute("news", responseDto.getNews());

        return "main";
    }

    @GetMapping("/post/{username}")
    public String post(@PathVariable String username, Model model) {

        model.addAttribute("user", userService.getUser(username));
        model.addAttribute("post", myvelogService.post(username));

        return "post";
    }

    @GetMapping("/day/{username}")
    public String day(@PathVariable String username, @RequestParam(required = false) LocalDate date, Model model) {

        model.addAttribute("user", userService.getUser(username));
        model.addAttribute("day",myvelogService.day(username, date));

        return "day";
    }
}
