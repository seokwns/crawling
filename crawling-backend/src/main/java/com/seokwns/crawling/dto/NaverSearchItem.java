package com.seokwns.crawling.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record NaverSearchItem(
        String title,
        String originallink,
        String link,
        String description,
        @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss Z", locale = "en")
        LocalDateTime pubDate
) {
}
