package com.pbru.it.backend.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/files")
public class FileDownloadController {
    
    @GetMapping("/courses/download")
    public ResponseEntity<Resource> dowloadFile() throws IOException {

        final String filename = "courses.xlsx";
        final Resource file = new ClassPathResource("static/files/" + filename);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        String encodeedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(
            ContentDisposition.builder("attachment")
                    .filename(encodeedFilename, StandardCharsets.UTF_8)
                    .build()
        );

        return ResponseEntity.ok().headers(headers).body(file);

    }

}
