package com.yevini.myvelog;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TimeoutException.class)
    public String handleTimeoutException(Exception e, Model model) {
        log.info("[TimeoutException]");
        model.addAttribute("timeout", true);
        return "index";
    }


    @ExceptionHandler(value = JsonProcessingException.class)
    public String handleJsonProcessingException(Exception e) {
        log.error("[JsonProcessingException] "+ e.getMessage());
        return "index";
    }


    @ExceptionHandler(value = IllegalArgumentException.class)
    public String handleIllegalArgumentException(Exception e, Model model) {
        log.error("IllegalArgumentException");

        model.addAttribute("logout", true);
        return "index";
    }
}
