package com.seokwns.crawling.repository;

import com.seokwns.crawling.entity.Keyword;
import com.seokwns.crawling.entity.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Keyword> {
}
