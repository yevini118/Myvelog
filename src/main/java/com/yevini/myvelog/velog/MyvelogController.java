package com.yevini.myvelog.velog;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MyvelogController {

    private final MyvelogService myvelogService;

    @RequestMapping("")
    public String index() {

        return "index";
    }

    @RequestMapping("/main/{username}")
    public String main(@PathVariable String username) {

        myvelogService.main(username);
        return "main";
    }

    @GetMapping("/login")
    public String login() throws JsonProcessingException {

        myvelogService.login();
        return "redirect:/main";
    }
}
