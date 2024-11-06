package com.seokwns.crawling.dto;

import java.time.LocalDateTime;

public record KeywordDto(
        String keyword,
        LocalDateTime lastCrawlingTime
) {
}
