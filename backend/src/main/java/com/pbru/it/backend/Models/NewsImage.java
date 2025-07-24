package com.pbru.it.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "news_image")
public class NewsImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_image_id")
    private Long id;

    @Column(name = "news_image_path", columnDefinition = "TEXT", nullable = true)
    private String path;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "news_id",referencedColumnName = "news_id", nullable = false)
    private News news;

    public NewsImage() {
    }

    public NewsImage(Long id, String path, News news) {
        this.id = id;
        this.path = path;
        this.news = news;
    }

    public NewsImage(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

}
