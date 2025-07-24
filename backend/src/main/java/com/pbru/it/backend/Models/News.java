package com.pbru.it.backend.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "news")
public class News extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Column(name = "news_title", nullable = false)
    private String title;

    @Column(name = "news_link_name", nullable = true)
    private String linkName;

    @Column(name = "news_link",columnDefinition = "TEXT" , nullable = true)
    private String link;

    @Column(name = "news_detail", columnDefinition = "TEXT", nullable = false)
    private String detail;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsImage> newsImages = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public News() {
    }

    public News(Long id, String title, String linkName, String link, String detail, List<NewsImage> newsImages,
            LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.linkName = linkName;
        this.link = link;
        this.detail = detail;
        this.newsImages = newsImages;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<NewsImage> getNewsImages() {
        return newsImages;
    }

    public void setNewsImages(List<NewsImage> newsImages) {
        this.newsImages = newsImages;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
