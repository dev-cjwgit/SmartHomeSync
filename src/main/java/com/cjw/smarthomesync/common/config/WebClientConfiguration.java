package com.cjw.smarthomesync.common.config;


import com.cjw.smarthomesync.common.exception.BaseException;
import com.cjw.smarthomesync.common.exception.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfiguration {
    @Value("${smart-things.auth-token}")
    private String smartThingsAuthToken;

    @Bean
    public WebClient provideWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.smartthings.com/v1")
                .defaultHeader("Accept", "application/vnd.smartthings+json;v=1")
                .defaultHeader("Authorization", "Bearer " + smartThingsAuthToken)
                .defaultStatusHandler(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new BaseException(ErrorMessage.EXTERNAL_4XX_SMART_THINGS_EXCEPTION))
                )
                .defaultStatusHandler(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new BaseException(ErrorMessage.EXTERNAL_5XX_SMART_THINGS_EXCEPTION))
                )
                .build();
    }
}
