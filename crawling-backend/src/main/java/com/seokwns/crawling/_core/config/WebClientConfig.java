package com.seokwns.crawling._core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.search.naver.id}")
    private String CLIENT_ID;

    @Value("${api.search.naver.secret}")
    private String CLIENT_SECRET;

    @Bean(name = "naverWebClient")
    public WebClient naverWebClient() {
        String REQUEST_URL = "https://openapi.naver.com/v1/search/news.json";
        return WebClient.builder()
                .baseUrl(REQUEST_URL)
                .defaultHeader("X-Naver-Client-Id", CLIENT_ID)
                .defaultHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(100 * 1024 * 1024))
                        .build())
                .build();
    }
}
