package com.pbru.it.backend.DTO.request;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDemoRequest {
    private Long id;
    private String title;
    private String linkName;
    private String link;
    private String detail;
    private Set<MultipartFile> images = new HashSet<MultipartFile>();
}
