package com.pbru.it.backend.Services;

import java.io.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.pbru.it.backend.Models.QMajor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pbru.it.backend.DTO.request.MajorRequest;
import com.pbru.it.backend.Models.Major;
import com.pbru.it.backend.Models.MajorCourse;
import com.pbru.it.backend.Models.Program;
import com.pbru.it.backend.Repositories.MajorRepository;
import com.pbru.it.backend.Repositories.ProgramRepository;

@Service
public class MajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private FileService fileService;

    public Page<Major> findAll(Pageable pageable) {
        return majorRepository.findAll(pageable);
    }

    public Page<Major> filter(String nameTh, String nameEn, Integer degreeId, Boolean status, Pageable pageable) {

        QMajor major = QMajor.major;
        BooleanBuilder builder = new BooleanBuilder();

        if (nameTh != null && !nameTh.isEmpty()) {
            builder.and(major.nameTh.containsIgnoreCase(nameTh));
        }

        if (nameEn != null && !nameEn.isEmpty()) {
            builder.and(major.nameEn.containsIgnoreCase(nameEn));
        }

        if (degreeId != null) {
            builder.and(major.program.degree.id.eq(degreeId));
        }

        if (status != null) {
            if (status) {
                builder.and(major.status.isTrue());
            } else {
                builder.and(major.status.isFalse());
            }
        }

        Predicate predicate = builder.getValue();

        if (predicate != null)
            return majorRepository.findAll(predicate, pageable);

        return majorRepository.findAll(pageable);

    }

    public Page<Major> findByNameThContainingIgnoreCaseOrNameEnContainingIgnoreCase(String nameTh, String nameEn,
            Pageable pageable) {
        return majorRepository.findByNameThContainingIgnoreCaseOrNameEnContainingIgnoreCase(nameTh, nameEn, pageable);
    }

    public Optional<Major> findById(Long id) {
        return majorRepository.findById(id);
    }

    @Transactional
    public Major save(MajorRequest majorRequest) {
        Major major = new Major();
        Program program = new Program();
        program.setId(majorRequest.getProgramId());
        major.setProgram(program);
        major.setNameTh(majorRequest.getNameTh());
        major.setNameEn(majorRequest.getNameEn());
        major.setTuitionFees(majorRequest.getTuitionFees());
        major.setLanguage(majorRequest.getLanguage());
        major.setCareers(majorRequest.getCareers());
        major.setDescription(majorRequest.getDescription());
        major.setStatus(majorRequest.getStatus());

        // เช็คว่า image มีไหมก่อนอัปโหลด
        if (majorRequest.getImage() != null && !majorRequest.getImage().isEmpty()) {
            major.setImage(fileService.uploadImageFile(majorRequest.getImage()));
        }

        // เช็คว่า courseFile มีไหมก่อนเซ็ต
        if (majorRequest.getCourseFile() != null && !majorRequest.getCourseFile().isEmpty()) {
            major.setCourses(CsvToSetMajorCourse(majorRequest.getCourseFile(), major));
        }

        return majorRepository.save(major);
    }

    @Transactional
    public Major updateById(Long id, MajorRequest majorRequest) {
        Optional<Major> optionalMajor = majorRepository.findById(id);
        if (optionalMajor.isEmpty()) {
            throw new RuntimeException("Major not found!");
        }

        Major major = optionalMajor.get();

        // อัปเดตข้อมูลทั่วไป
        Program program = programRepository.findById(majorRequest.getProgramId())
                .orElseThrow(() -> new RuntimeException("Program not found"));

        major.setProgram(program);
        major.setNameTh(majorRequest.getNameTh());
        major.setNameEn(majorRequest.getNameEn());
        major.setTuitionFees(majorRequest.getTuitionFees());
        major.setLanguage(majorRequest.getLanguage());
        major.setCareers(majorRequest.getCareers());
        major.setDescription(majorRequest.getDescription());
        major.setStatus(majorRequest.getStatus());

        // ถ้ามีการอัปโหลดรูปภาพใหม่
        if (majorRequest.getImage() != null && !majorRequest.getImage().isEmpty()) {
            String newImageUrl = fileService.uploadImageFile(majorRequest.getImage());
            fileService.deleteImageFile(major.getImage());
            major.setImage(newImageUrl);
        }

        // ถ้ามีไฟล์หลักสูตรใหม่ ให้แทนที่ของเก่า
        if (majorRequest.getCourseFile() != null && !majorRequest.getCourseFile().isEmpty()) {
            major.getCourses().clear();
            major.getCourses().addAll(CsvToSetMajorCourse(majorRequest.getCourseFile(), major));
        }

        return majorRepository.save(major);
    }

    @Transactional
    public Major setStatusMajor(Long id, boolean status) {
        Optional<Major> optionalMajor = majorRepository.findById(id);
        if (optionalMajor.isEmpty()) {
            throw new RuntimeException("Major not found!");
        }
        Major major = optionalMajor.get();
        major.setStatus(status);
        return majorRepository.save(major);
    }

    public void deleteById(Long id) {
        Optional<Major> major = majorRepository.findById(id);
        major.ifPresent(value -> fileService.deleteImageFile(value.getImage()));
        majorRepository.delete(major.get());
    }

    private Set<MajorCourse> CsvToSetMajorCourse(MultipartFile file, Major major) {
        try (
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReader(reader);) {
            Set<MajorCourse> courses = new HashSet<>();
            String[] nextLine;
            boolean isFirstLine = true;
            while ((nextLine = csvReader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                MajorCourse course = new MajorCourse();
                course.setYear(Integer.parseInt(nextLine[0]));
                course.setSector(Integer.parseInt(nextLine[1]));
                course.setCode(nextLine[2]);
                course.setNameTh(nextLine[3]);
                course.setNameEn(nextLine[4]);
                course.setCredit(Integer.parseInt(nextLine[5]));
                course.setMajor(major);
                courses.add(course);
            }
            return courses;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error while reading CSV: " + e.getMessage());
        }
    }

}
