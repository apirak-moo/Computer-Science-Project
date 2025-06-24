package com.pbru.it.backend.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbru.it.backend.Models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findByTitle(String title, Pageable pageable);
    Page<News> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
