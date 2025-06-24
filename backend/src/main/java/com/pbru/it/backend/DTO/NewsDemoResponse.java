package com.pbru.it.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDemoResponse {
    private Long id;
    private String title;
    private String detail;
    private List<NewsLinkResponse> newsLinks;
    private NewsImageResponse newsImage;
}
