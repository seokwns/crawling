package com.seokwns.crawling.dto;

import java.util.List;

public record NaverSearchResponse(
        String lastBuildDate,
        Long total,
        Long start,
        Long display,
        List<NaverSearchItem> items
) {
}
