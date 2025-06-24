package com.pbru.it.backend.Controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pbru.it.backend.DTO.NewsDemoRequest;
import com.pbru.it.backend.Models.News;
import com.pbru.it.backend.Services.NewsService;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<Page<News>> findAll(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 8, sort = { "createdAt" }, direction = Sort.Direction.DESC) Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(newsService.findByTitleContainingIgnoreCase(search, pageable));
        }
        return ResponseEntity.ok(newsService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> findById(@PathVariable Long id) {
        return newsService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<News> save(@ModelAttribute NewsDemoRequest news) {
        News newsDemo = newsService.save(news);
        if (newsDemo == null)
            return ResponseEntity.notFound().build();
        URI location = URI.create("/news/" + newsDemo.getId());
        return ResponseEntity.created(location).body(newsDemo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateById(@PathVariable Long id, @ModelAttribute NewsDemoRequest news) {
        return ResponseEntity.ok(newsService.updateById(id, news));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
