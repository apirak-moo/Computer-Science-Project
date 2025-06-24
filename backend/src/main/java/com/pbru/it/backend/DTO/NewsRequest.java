package com.pbru.it.backend.DTO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsRequest {

    private Long id;

    private String title;

    private Set<MultipartFile> images = new HashSet<>();

}
