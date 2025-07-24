package com.pbru.it.backend.Services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.pbru.it.backend.DTO.BranchMajor;
import com.pbru.it.backend.DTO.response.BranchMiniResponse;
import com.pbru.it.backend.DTO.request.BranchRequest;
import com.pbru.it.backend.DTO.response.BranchResponse;
import com.pbru.it.backend.Models.*;
import com.pbru.it.backend.Repositories.BranchRepository;
import com.pbru.it.backend.Repositories.MajorRepository;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private ModelMapper modelMapper;

    public List<BranchResponse> findAll() {
        List<BranchResponse> branchResponses = branchRepository.findAll()
                .stream()
                .map(branch -> new BranchResponse(
                        branch.getId(),
                        branch.getMajor() != null
                                ? new BranchMajor(
                                        branch.getMajor().getId(),
                                        branch.getMajor().getProgram(),
                                        branch.getMajor().getNameTh(),
                                        branch.getMajor().getNameEn(),
                                        branch.getMajor().getTuitionFees(),
                                        branch.getMajor().getLanguage(),
                                        branch.getMajor().getImage(),
                                        branch.getMajor().getDescription(),
                                        branch.getMajor().getCareers(),
                                        branch.getMajor().getStatus())
                                : null,
                        branch.getNameTh(),
                        branch.getNameEn(),
                        branch.getTuitionFees(),
                        branch.getLanguage(),
                        branch.getCourses(),
                        branch.getDescription(),
                        branch.getImage(),
                        branch.getCareers(),
                        branch.isStatus()))
                .collect(Collectors.toList());
        return branchResponses;
    }

    public List<BranchMiniResponse> filterMiniBranch(Boolean status) {

        QBranch branch = QBranch.branch;
        BooleanBuilder builder = new BooleanBuilder();

        if (status != null) {
            if (status) {
                builder.and(branch.status.isTrue());
            } else {
                builder.and(branch.status.isFalse());
            }
        }

        Predicate predicate = builder.getValue();

        Sort sort = Sort.by(
                Sort.Order.desc("status"),
                Sort.Order.desc("id"),
                Sort.Order.desc("major.id"),
                Sort.Order.desc("nameEn"));

        if (predicate != null)
            return StreamSupport.stream(branchRepository.findAll(predicate, sort).spliterator(), false)
                    .map(branchMap -> modelMapper.map(branchMap, BranchMiniResponse.class))
                    .collect(Collectors.toList());

        return branchRepository.findAll(sort).stream()
                .map(branchMap -> modelMapper.map(branchMap, BranchMiniResponse.class))
                .collect(Collectors.toList());

    }

    public BranchResponse findById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return modelMapper.map(branch, BranchResponse.class);
    }

    public int getCountBranch() {
        QBranch branch = QBranch.branch;
        Long count = jpaQueryFactory.select(Wildcard.count)
                    .from(branch)
                    .where(branch.status.eq(true))
                    .fetchOne();
        return count != null ? count.intValue() : 0;
    }

    @Transactional
    public Branch save(BranchRequest branchRequest) {
        Branch branch = new Branch();
        Major major = new Major();
        major.setId(branchRequest.getMajorId());
        branch.setMajor(major);
        branch.setNameTh(branchRequest.getNameTh());
        branch.setNameEn(branchRequest.getNameEn());
        branch.setTuitionFees(branchRequest.getTuitionFees());
        branch.setLanguage(branchRequest.getLanguage());
        branch.setCareers(branchRequest.getCareers());
        branch.setDescription(branchRequest.getDescription());
        branch.setStatus(branchRequest.getStatus());
        branch.setImage("");

        if(branchRequest.getImage() != null && !branchRequest.getImage().isEmpty()){
            branch.setImage(fileService.uploadImageFile(branchRequest.getImage()));
        }
        
        if(branchRequest.getCourseFile() != null && !branchRequest.getCourseFile().isEmpty()){
            branch.setCourses(CsvToSetMajorCourse(branchRequest.getCourseFile(), branch));
        }
        
        return branchRepository.save(branch);
    }

    @Transactional
    public Branch updateById(Long id, BranchRequest branchRequest) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if (optionalBranch.isEmpty()) {
            throw new RuntimeException("Branch not found!");
        }

        Branch branch = optionalBranch.get();

        // อัปเดตข้อมูลทั่วไป
        Major major = majorRepository.findById(branchRequest.getMajorId())
                .orElseThrow(() -> new RuntimeException("Major not found"));

        branch.setMajor(major);
        branch.setNameTh(branchRequest.getNameTh());
        branch.setNameEn(branchRequest.getNameEn());
        branch.setTuitionFees(branchRequest.getTuitionFees());
        branch.setLanguage(branchRequest.getLanguage());
        branch.setCareers(branchRequest.getCareers());
        branch.setDescription(branchRequest.getDescription());
        branch.setStatus(branchRequest.getStatus());

        // ถ้ามีการอัปโหลดรูปภาพใหม่
        if (branchRequest.getImage() != null && !branchRequest.getImage().isEmpty()) {
            String newImageUrl = fileService.uploadImageFile(branchRequest.getImage());
            fileService.deleteImageFile(branch.getImage());
            branch.setImage(newImageUrl);
        }

        // ถ้ามีไฟล์หลักสูตรใหม่ ให้แทนที่ของเก่า
        if (branchRequest.getCourseFile() != null && !branchRequest.getCourseFile().isEmpty()) {
            Set<BranchCourse> newCourses = new HashSet<>(CsvToSetMajorCourse(branchRequest.getCourseFile(), branch));
            branch.getCourses().clear();
            branch.getCourses().addAll(newCourses);
        }

        return branchRepository.save(branch);
    }

    public void setStatusBranch(Long id, Boolean status) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        branch.setStatus(status);
        branchRepository.save(branch);
    }

    public void deleteById(Long id) {
        Optional<Branch> branch = branchRepository.findById(id);
        branch.ifPresent(value -> fileService.deleteImageFile(value.getImage()));
        branchRepository.delete(branch.get());
    }

    private Set<BranchCourse> CsvToSetMajorCourse(MultipartFile file, Branch branch) {
        try (
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReader(reader);) {
            Set<BranchCourse> courses = new HashSet<>();
            String[] nextLine;
            boolean isFirstLine = true;
            while ((nextLine = csvReader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                BranchCourse course = new BranchCourse();
                course.setYear(Integer.parseInt(nextLine[0]));
                course.setSector(Integer.parseInt(nextLine[1]));
                course.setCode(nextLine[2]);
                course.setNameTh(nextLine[3]);
                course.setNameEn(nextLine[4]);
                course.setCredit(Integer.parseInt(nextLine[5]));
                course.setBranches(branch);
                courses.add(course);
            }
            return courses;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error while reading CSV: " + e.getMessage());
        }
    }

}
