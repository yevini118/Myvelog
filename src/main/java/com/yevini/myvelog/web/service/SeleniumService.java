package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevini.myvelog.web.dto.response.CurrentUser;
import com.yevini.myvelog.web.dto.response.User;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SeleniumService{

    private WebDriver driver;

    private static final String URL = "https://velog.io/";
    private static final String LOGIN_BUTTON_CLASS_NAME = "sc-bqiRlB";
    private static final String USER_PROFILE_CLASS_NAME = "sc-fotOHu";


    public User process() throws JsonProcessingException {

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\Downloads\\chromedriver_win32\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        driver = new ChromeDriver(chromeOptions);
        driver.get(URL);
        driver.findElement(By.className(LOGIN_BUTTON_CLASS_NAME)).click();


        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(3));
        try
        {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(USER_PROFILE_CLASS_NAME)));
        } catch (TimeoutException e) {
            driver.quit();
            throw new TimeoutException();
        }

        CurrentUser currentUser = getCurrentUser();
        String accessToken = getAccessToken();

        driver.quit();
        return new User(currentUser, accessToken);
    }


    private String getAccessToken(){

        return driver.manage().getCookieNamed("access_token").getValue();
    }


    private CurrentUser getCurrentUser() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        WebStorage storage = (WebStorage)driver;

        LocalStorage localStorage = storage.getLocalStorage();
        String currentUser = localStorage.getItem("CURRENT_USER");

        return objectMapper.readValue(currentUser, CurrentUser.class);
    }
}
