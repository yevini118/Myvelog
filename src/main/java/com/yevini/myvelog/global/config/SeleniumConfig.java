package com.yevini.myvelog.global.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("selenium")
@Getter
@RequiredArgsConstructor
public class SeleniumConfig {

    private final String chromePath;
    private final String chromeDataPath;
    private final String chromedriverPath;

}