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

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) throws InterruptedException, JsonProcessingException {

        MainResponseDto responseDto = myvelogService.main(customUserDetails.getUsername(), customUserDetails.getPassword());


        model.addAttribute("user", customUserDetails.getUser());
        model.addAttribute("stats", responseDto.getMyvelogStats());
        model.addAttribute("news", responseDto.getNews());

        return "main";
    }

    @GetMapping("/post")
    public String post(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        model.addAttribute("user", customUserDetails.getUser());
        model.addAttribute("post", myvelogService.post(customUserDetails.getUsername()));

        return "post";
    }

    @GetMapping("/day")
    public String day(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(required = false) LocalDate date, Model model) {

        model.addAttribute("user", customUserDetails.getUser());
        model.addAttribute("day", myvelogService.day(customUserDetails.getUsername(), date));

        return "day";
    }
}
