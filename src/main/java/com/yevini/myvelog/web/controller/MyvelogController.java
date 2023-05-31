package com.yevini.myvelog.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.web.dto.DayResponseDto;
import com.yevini.myvelog.web.dto.MainResponseDto;
import com.yevini.myvelog.web.dto.response.User;
import com.yevini.myvelog.web.service.MyvelogService;
import com.yevini.myvelog.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String main(@PathVariable String username, Model model) throws InterruptedException, JsonProcessingException {

        User user = userService.getUser(username);
        MainResponseDto responseDto = myvelogService.main(username, user.getAccessToken());
        model.addAttribute("User", user);
        model.addAttribute("stats", responseDto.getMyvelogStats());
        model.addAttribute("news", responseDto.getNews());
        model.addAttribute("isLikesUp", responseDto.isLikesUp());

        return "main";
    }

    @GetMapping("/post/{username}")
    public String post(@PathVariable String username, Model model) {

        model.addAttribute("user", userService.getUser(username));
        model.addAttribute("stats", myvelogService.post(username).getPostStats());

        return "post";
    }

    @GetMapping("/day/{username}")
    public String day(@PathVariable String username, @RequestParam(required = false) LocalDate date, Model model) {

        model.addAttribute("User", userService.getUser(username));
        model.addAttribute("Day",myvelogService.day(username, date));

        return "day";
    }
}
