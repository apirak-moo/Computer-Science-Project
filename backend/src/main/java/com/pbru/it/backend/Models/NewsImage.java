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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "news_image")
public class NewsImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_image_id")
    private Long id;

    @Column(name = "news_image_path", columnDefinition = "TEXT", nullable = true)
    private String path;

    public NewsImage() {
    }

    public NewsImage(String path) {
        this.path = path;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "news_id",referencedColumnName = "news_id", nullable = false)
    private News news;

}
