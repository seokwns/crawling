package com.seokwns.crawling.service;

import com.seokwns.crawling.dto.*;
import com.seokwns.crawling.entity.Keyword;
import com.seokwns.crawling.entity.NewsItem;
import com.seokwns.crawling.repository.KeywordRepository;
import com.seokwns.crawling.repository.NewsItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SearchService {
    private final KeywordRepository keywordRepository;
    private final NewsItemRepository newsItemRepository;
    private final WebClient naverWebClient;

    public SearchService(@Autowired KeywordRepository keywordRepository,
                         @Autowired NewsItemRepository newsItemRepository,
                         @Autowired WebClient naverWebClient
    ) {
        this.keywordRepository = keywordRepository;
        this.newsItemRepository = newsItemRepository;
        this.naverWebClient = naverWebClient;
    }

    @Scheduled(cron = "0 30 8 * * *", zone = "Asia/Seoul")
    public void scheduler() {
        List<Keyword> keywords = this.keywordRepository.findAll();
        keywords.parallelStream().forEach(this::fetchAndSaveNewsForKeyword);
    }

    private void fetchAndSaveNewsForKeyword(Keyword keyword) {
        LocalDateTime lastCrawlingTime = Optional.ofNullable(keyword.getLastCrawlingTime())
                .orElse(LocalDateTime.now().minusDays(1).withHour(8).withMinute(30));

        Optional<NaverSearchResponse> response = this.naverWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", keyword.getWord())
                        .queryParam("display", 30)
                        .queryParam("start", 1)
                        .queryParam("sort", "date")
                        .build())
                .retrieve()
                .bodyToFlux(NaverSearchResponse.class)
                .toStream()
                .findFirst();

        response.ifPresent(
                naverSearchResponse -> {
                    for (NaverSearchItem item : naverSearchResponse.items()) {
                        log.debug("title {}", item.title());
                        if (item.pubDate().isBefore(lastCrawlingTime)) {
                            break;
                        }
                        saveNewsItem(keyword.getWord(), item);
                    }
                }
        );

        keyword.setLastCrawlingTime(LocalDateTime.now());
        this.keywordRepository.save(keyword);
    }

    private void saveNewsItem(String keyword, NaverSearchItem item) {
        NewsItem newsItem = NewsItem.builder()
                .keyword(keyword)
                .timestamp(item.pubDate())
                .title(item.title())
                .description(item.description())
                .link(item.originallink())
                .build();

        this.newsItemRepository.save(newsItem);
    }

    public void addKeyword(AddKeywordRequest request) {
        if (this.keywordRepository.existsByWord(request.keyword())) {
            return;
        }

        Keyword key = Keyword.builder().word(request.keyword()).build();
        this.keywordRepository.save(key);
    }

    public List<KeywordDto> getKeywords() {
        List<Keyword> keywords = this.keywordRepository.findAll();
        return keywords.stream().map(keyword -> new KeywordDto(keyword.getWord(), keyword.getLastCrawlingTime())).toList();
    }

    public void deleteKeyword(String keyword) {
        this.keywordRepository.deleteByWord(keyword);
    }

    public List<NewsItemDto> findNews(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<NewsItem> items = this.newsItemRepository.findAll(pageable).getContent();

        return items.stream().map(newsItem ->
                new NewsItemDto(
                        newsItem.getKeyword(),
                        newsItem.getTimestamp(),
                        newsItem.getTitle(),
                        newsItem.getDescription(),
                        newsItem.getLink()
                )
        ).toList();
    }
}
