package com.yevini.myvelog.web.controller;

import com.yevini.myvelog.global.security.CustomUserDetails;
import com.yevini.myvelog.model.monbodb.Subscribe;
import com.yevini.myvelog.model.response.UserProfile;
import com.yevini.myvelog.repository.SubscribeRepository;
import com.yevini.myvelog.web.dto.SubscribeResponseDto;
import com.yevini.myvelog.web.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SubscribeController {

    private final SubscribeService subscribeService;

    @GetMapping("/feed")
    public String feed(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return "feed";
    }

    @PostMapping("/subscribe")
    public String add(@RequestParam(required = false) String subscribe, @AuthenticationPrincipal CustomUserDetails customUserDetails, RedirectAttributes redirectAttributes){

        System.out.println("add");
        boolean save = subscribeService.save(customUserDetails.getUsername(), subscribe);

        redirectAttributes.addFlashAttribute("save", save);

        return "redirect:/subscribe";
    }

    @GetMapping("/subscribe")
    public String getSubscribes(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        List<SubscribeResponseDto> responseDtos = subscribeService.getSubscribes(customUserDetails.getUsername());
        model.addAttribute("userProfiles", responseDtos);

        return "subscribe";
    }

    @DeleteMapping("/subscribe/{subscribeId}")
    public String delete(@PathVariable String subscribeId, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        subscribeService.delete(subscribeId, customUserDetails.getUsername());
        return "redirect:/subscribe";
    }
}
