package com.pbru.it.backend.Models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;

import lombok.EqualsAndHashCode;

@Builder
@Entity
@Table(name = "majors")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program", referencedColumnName = "program_id", nullable = false)
    private Program program;

    @Column(name = "major_name_th", length = 100, nullable = false)
    private String nameTh;

    @Column(name = "major_name_en", length = 100, nullable = false)
    private String nameEn;

    @Column(name = "major_tuition_fees", precision = 10, scale = 2)
    private BigDecimal tuitionFees;

    @Enumerated(EnumType.STRING)
    @Column(name = "major_language", length = 30, nullable = false)
    private CourseLanguage language;

    @Builder.Default
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MajorCourse> courses = new HashSet<>();

    @Column(name = "major_image", columnDefinition = "TEXT", nullable = false)
    private String image;

    @Column(name = "major_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "major_careers", columnDefinition = "TEXT", nullable = false)
    private String careers;

    @Column(name = "major_status", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean status;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Branch> branches = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public BigDecimal getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(BigDecimal tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public CourseLanguage getLanguage() {
        return language;
    }

    public void setLanguage(CourseLanguage language) {
        this.language = language;
    }

    public Set<MajorCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<MajorCourse> courses) {
        this.courses = courses;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCareers() {
        return careers;
    }

    public void setCareers(String careers) {
        this.careers = careers;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Branch> getBranches() {
        return branches;
    }

    public void setBranches(Set<Branch> branches) {
        this.branches = branches;
    }

    public Major(Long id, Program program, String nameTh, String nameEn, BigDecimal tuitionFees,
            CourseLanguage language, Set<MajorCourse> courses, String image, String description, String careers,
            Boolean status, Set<Branch> branches) {
        this.id = id;
        this.program = program;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.tuitionFees = tuitionFees;
        this.language = language;
        this.courses = courses;
        this.image = image;
        this.description = description;
        this.careers = careers;
        this.status = status;
        this.branches = branches;
    }

    public Major() {
    }

}
