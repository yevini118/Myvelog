package com.yevini.myvelog.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevini.myvelog.model.response.CurrentUser;
import com.yevini.myvelog.model.velog.User;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

@Service
public class SeleniumService{

    private WebDriver driver;
    private final ChromeOptions chromeOptions;
    private static final String URL = "https://velog.io/";

    private static final String CHROME_PATH = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

    private static final String CHROME_DATA_PATH = "C:/Selenium/ChromeData";

    private static final String CHROMEDRIVER_PATH = "C:\\Users\\USER\\Downloads\\chromedriver.exe";

    private static final String LOGIN_BUTTON_CLASS_NAME = "sc-egiyK";
    private static final String USER_PROFILE_CLASS_NAME = "sc-hBUSln";
    private static final String REFRESH_TOKEN_CLASS_NAME = "sc-fFeiMQ";

    private static final int LOGIN_MAX_MINUTE = 3;

    public SeleniumService() {

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);

        this.chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
    }

    public User login() throws JsonProcessingException , IOException{

        openLoginPage();
        waitUntilLogin();

        CurrentUser currentUser = getCurrentUser();
        String accessToken = getAccessToken();

        driver.close();

        return User.builder()
                .username(currentUser.getUsername())
                .displayName(currentUser.getDisplayName())
                .thumbnail(currentUser.getThumbnail())
                .accessToken(accessToken)
                .build();
    }

    public void logout() {

        FileSystemUtils.deleteRecursively(new File(CHROME_DATA_PATH));
    }

    private void openLoginPage() throws IOException {

        Runtime.getRuntime().exec( CHROME_PATH + " --remote-debugging-port=9222 --user-data-dir=" + CHROME_DATA_PATH);

        driver = new ChromeDriver(chromeOptions);
        driver.get(URL);

        if (!isAccessTokenExist() && !isRefreshTokenExist()) {
            driver.findElement(By.className(LOGIN_BUTTON_CLASS_NAME)).click();
        }
        else if (!isAccessTokenExist()) {
            driver.get(URL + "saves");
        }
    }

    private void waitUntilLogin() {

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(LOGIN_MAX_MINUTE));

        try {
            webDriverWait.until(loginCondition());
        } catch (TimeoutException e) {
            driver.close();
            throw new TimeoutException();
        }
    }

    private boolean isAccessTokenExist() {

        return driver.manage().getCookieNamed("access_token") != null;
    }

    private boolean isRefreshTokenExist() {

        return driver.manage().getCookieNamed("refresh_token") != null;
    }


    private String getAccessToken(){

        return driver.manage().getCookieNamed("access_token").getValue();
    }


    private CurrentUser getCurrentUser() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        LocalStorage localStorage = getLocalStorage();

        String currentUser = localStorage.getItem("CURRENT_USER");

        return objectMapper.readValue(currentUser, CurrentUser.class);
    }

    private static ExpectedCondition<WebElement> loginCondition() {
        return ExpectedConditions.visibilityOfElementLocated(By.className(USER_PROFILE_CLASS_NAME));
    }

    private LocalStorage getLocalStorage() {

        WebStorage storage = (WebStorage)driver;
        return storage.getLocalStorage();
    }
}
