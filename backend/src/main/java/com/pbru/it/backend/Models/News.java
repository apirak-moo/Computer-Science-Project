package com.pbru.it.backend.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
