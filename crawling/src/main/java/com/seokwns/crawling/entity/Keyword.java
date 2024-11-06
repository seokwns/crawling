package com.seokwns.crawling.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Keyword {
    @Id
    private String word;

    @Column
    private LocalDateTime lastCrawlingTime;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Builder
    public Keyword(String word, LocalDateTime lastCrawlingTime) {
        this.word = word;
        this.lastCrawlingTime = lastCrawlingTime;
    }

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setLastCrawlingTime(LocalDateTime lastCrawlingTime) {
        this.lastCrawlingTime = lastCrawlingTime;
    }
}
