package com.yevini.myvelog.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() throws JsonProcessingException, IOException {

        String username = userService.login();
        log.info("[Login] {}", username);

        return "redirect:/main/" + username;
    }

    @GetMapping("/logout/{username}")
    public String logout(@PathVariable String username) {

        userService.logout(username);
        log.info("[Logout] {}", username);

        return "redirect:/";
    }
}
