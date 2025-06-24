package com.pbru.it.backend.DTO;

import com.pbru.it.backend.Models.NewsImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsImageResponse {

    private Long id;
    private String path;

}
