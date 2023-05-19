package com.yevini.myvelog.velog;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MyvelogController {

    private final MyvelogService myvelogService;

    @ModelAttribute

    @RequestMapping("")
    public String index() {

        return "index";
    }

    @GetMapping("/main/{username}")
    public String main(@PathVariable String username, Model model) throws InterruptedException, JsonProcessingException {

        myvelogService.main(username, model);
        return "main";
    }

    @GetMapping("/post/{username}")
    public String post(@PathVariable String username, Model model) {

        myvelogService.post(username, model);
        return "post";
    }

    @GetMapping("/login")
    public String login() throws JsonProcessingException {

        String username = myvelogService.login();
        return "redirect:/main/" + username;
    }

    @GetMapping("/logout/{username}")
    public String logout(@PathVariable String username) {

        myvelogService.logout(username);
        return "redirect:/";
    }

}
