package com.cjw.smarthomesync.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Value("${smart-things.auth-token}")
    private String smartThingsAuthToken;

    @Bean
    public WebClient provideWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.smartthings.com/v1")
                .defaultHeader("Accept", "application/vnd.smartthings+json;v=1")
                .defaultHeader("Authorization","Bearer " + smartThingsAuthToken)
                .build();
    }
}
