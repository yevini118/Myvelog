package com.yevini.myvelog;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.springframework.boot.Banner;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = NoSuchWindowException.class)
//    public String handleNoSuchWindowException(Exception e) {
//        log.error("NoSuchWindowException");
//        return "index";
//    }

    @ExceptionHandler(value = JsonProcessingException.class)
    public String handleJsonProcessingException(Exception e) {
        log.error("JsonProcessingException");
        return "index";
    }

    @ExceptionHandler(value = TimeoutException.class)
    public String handleTimeoutException(Exception e, Model model) {
        log.error("TimeoutException");

        model.addAttribute("fail", true);
        return "index";
    }


}
