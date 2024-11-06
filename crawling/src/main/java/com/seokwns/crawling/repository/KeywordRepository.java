package com.seokwns.crawling.repository;

import com.seokwns.crawling.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, String> {
    boolean existsByWord(String word);

    void deleteByWord(String word);
}
