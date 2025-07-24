package com.pbru.it.backend.DTO.response;

import java.util.List;
public class NewsDemoResponse {
    private Long id;
    private String title;
    private String detail;
    private List<NewsLinkResponse> newsLinks;
    private NewsImageResponse newsImage;
    public NewsDemoResponse() {
    }
    public NewsDemoResponse(Long id, String title, String detail, List<NewsLinkResponse> newsLinks,
            NewsImageResponse newsImage) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.newsLinks = newsLinks;
        this.newsImage = newsImage;
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
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public List<NewsLinkResponse> getNewsLinks() {
        return newsLinks;
    }
    public void setNewsLinks(List<NewsLinkResponse> newsLinks) {
        this.newsLinks = newsLinks;
    }
    public NewsImageResponse getNewsImage() {
        return newsImage;
    }
    public void setNewsImage(NewsImageResponse newsImage) {
        this.newsImage = newsImage;
    }
}
