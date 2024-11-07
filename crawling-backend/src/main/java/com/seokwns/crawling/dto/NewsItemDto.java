package com.seokwns.crawling.dto;

import java.time.LocalDateTime;

public record NewsItemDto(
        String keyword,
        LocalDateTime date,
        String title,
        String description,
        String link
) {
}
