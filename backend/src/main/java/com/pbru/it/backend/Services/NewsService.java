package com.pbru.it.backend.Services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pbru.it.backend.DTO.NewsDemoRequest;
import com.pbru.it.backend.Models.News;
import com.pbru.it.backend.Models.NewsImage;
import com.pbru.it.backend.Repositories.NewsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileService fileService;

    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    public Page<News> findByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return newsRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }

    @Transactional
    public News save(NewsDemoRequest newsDemoRequest) {

        News news = new News();
        news.setTitle(newsDemoRequest.getTitle());
        news.setDetail(newsDemoRequest.getDetail());
        news.setLinkName(newsDemoRequest.getLinkName());
        news.setLink(newsDemoRequest.getLink());

        Set<NewsImage> newsImages = new HashSet<>();

        for (MultipartFile item : newsDemoRequest.getImages()) {
            NewsImage newsImage = new NewsImage();
            newsImage.setNews(news);
            newsImage.setPath(fileService.uploadImageFile(item));
            newsImages.add(newsImage);
        }

        news.setNewsImages(newsImages);

        return newsRepository.save(news);
    }

    @Transactional
    public News updateById(Long id, NewsDemoRequest request) {

        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found with id " + id));

        // ลบไฟล์ที่เก่า (ถ้าจำเป็น)
        fileService.deleteAllByNewsImage(news.getNewsImages());

        // เคลียร์ภาพเก่า
        news.getNewsImages().clear();

        // เพิ่มภาพใหม่
        for (MultipartFile image : request.getImages()) {
            NewsImage newsImage = new NewsImage();
            newsImage.setNews(news);
            newsImage.setPath(fileService.uploadImageFile(image));
            news.getNewsImages().add(newsImage);
        }

        // อัปเดตข้อมูลทั่วไป
        news.setTitle(request.getTitle());
        news.setDetail(request.getDetail());
        news.setLinkName(request.getLinkName());
        news.setLink(request.getLink());

        return newsRepository.save(news);
    }

    public void deleteById(Long id) {
        Optional<News> newsDemoOpt = newsRepository.findById(id);
        if (newsDemoOpt.isPresent()) {
            News news = newsDemoOpt.get();
            fileService.deleteAllByNewsImage(news.getNewsImages());
            newsRepository.deleteById(id);
        }
    }

}
