package com.seokwns.crawling.controller;

import com.seokwns.crawling._core.response.Response;
import com.seokwns.crawling.dto.AddKeywordRequest;
import com.seokwns.crawling.dto.DeleteKeywordRequest;
import com.seokwns.crawling.dto.KeywordDto;
import com.seokwns.crawling.dto.NewsItemDto;
import com.seokwns.crawling.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crawling")
public class SearchController {
    private final SearchService searchService;

    public SearchController(@Autowired SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/keyword")
    public ResponseEntity<?> addKeyword(@RequestBody AddKeywordRequest request) {
        this.searchService.addKeyword(request);
        return ResponseEntity.ok().body(Response.success(null));
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> getKeyword() {
        List<KeywordDto> keywordDtos = this.searchService.getKeywords();
        return ResponseEntity.ok().body(Response.success(keywordDtos));
    }

    @DeleteMapping("/keyword")
    public ResponseEntity<?> deleteKeyword(@RequestBody DeleteKeywordRequest request) {
        this.searchService.deleteKeyword(request.keyword());
        return ResponseEntity.ok().body(Response.success(null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update() {
        this.searchService.scheduler();
        return ResponseEntity.ok().body(Response.success(null));
    }

    @GetMapping("")
    public ResponseEntity<?> getNews(@Param("offset") int offset, @Param("limit") int limit) {
        List<NewsItemDto> itemDtos = this.searchService.findNews(offset, limit);
        return ResponseEntity.ok().body(Response.success(itemDtos));
    }
}
