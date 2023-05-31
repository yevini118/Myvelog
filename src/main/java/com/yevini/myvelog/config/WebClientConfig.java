package com.yevini.myvelog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final String REQUEST_URL="https://v2cdn.velog.io/graphql";

    @Bean
    public WebClient webClient() {
        return WebClient.create(REQUEST_URL);
    }
}
