package com.pbru.it.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbru.it.backend.Models.News;
import com.pbru.it.backend.Models.NewsImage;

@Repository
public interface NewsImageRepository extends JpaRepository<NewsImage, Long> {
    void deleteAllByNews(News news);
}
