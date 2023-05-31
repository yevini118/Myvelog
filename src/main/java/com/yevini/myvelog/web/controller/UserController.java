package com.yevini.myvelog.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yevini.myvelog.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() throws JsonProcessingException {

        String username = userService.login();
        return "redirect:/main/" + username;
    }

    @GetMapping("/logout/{username}")
    public String logout(@PathVariable String username) {

        userService.logout(username);
        return "redirect:/";
    }
}
