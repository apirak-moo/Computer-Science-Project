package com.pbru.it.backend.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pbru.it.backend.DTO.ProfessorResearchWorkRequest;
import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Models.ProfessorResearchWork;
import com.pbru.it.backend.Models.ProfessorResearchWorkImage;
import com.pbru.it.backend.Repositories.ProfessorRepository;
import com.pbru.it.backend.Repositories.ProfessorResearchWorkRepository;

@Service
public class ProfessorResearchWorkService {

    @Autowired
    private ProfessorResearchWorkRepository professorResearchWorkRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private FileService fileService;

    public List<ProfessorResearchWork> getAllWorks() {
        return professorResearchWorkRepository.findAll();
    }

    public Optional<ProfessorResearchWork> getWork(Long id) {
        return professorResearchWorkRepository.findById(id);
    }

    public ProfessorResearchWork createWork(ProfessorResearchWorkRequest request) {
        ProfessorResearchWork work = new ProfessorResearchWork();
        work.setTitle(request.getTitle());
        work.setDescription(request.getDescription());
        Set<ProfessorResearchWorkImage> images = new HashSet<>();
        for (MultipartFile file : request.getImages()) {
            ProfessorResearchWorkImage image = new ProfessorResearchWorkImage();
            image.setPath(fileService.uploadImageFile(file));
            image.setWork(work);
            images.add(image);
        }
        work.setImages(images);
        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("ไม่พบ Professor Id : " + request.getProfessorId()));
        work.setProfessor(professor);
        return professorResearchWorkRepository.save(work);
    }

    public ProfessorResearchWork updateById(Long id, ProfessorResearchWorkRequest request) {
        ProfessorResearchWork work = professorResearchWorkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบ Work Id : " + id));
        work.setTitle(request.getTitle());
        work.setDescription(request.getDescription());
        if (!request.getImages().isEmpty()) {
            Set<ProfessorResearchWorkImage> images = new HashSet<>();
            for (MultipartFile file : request.getImages()) {
                ProfessorResearchWorkImage image = new ProfessorResearchWorkImage();
                image.setPath(fileService.uploadImageFile(file));
                image.setWork(work);
                images.add(image);
            }
            work.getImages().clear();
            work.getImages().addAll(images);
        }
        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("ไม่พบ Professor Id : " + request.getProfessorId()));
        work.setProfessor(professor);
        return professorResearchWorkRepository.save(work);
    }

    public void deleteById(Long id) {
        ProfessorResearchWork work = professorResearchWorkRepository.findById(id).orElseThrow(() -> new RuntimeException("ไม่พบ Work Id : " + id));
        for (ProfessorResearchWorkImage image : work.getImages()) {
            fileService.deleteImageFile(image.getPath());
        }
        professorResearchWorkRepository.deleteById(id);
    }

}
