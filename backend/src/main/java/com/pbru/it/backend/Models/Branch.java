package com.pbru.it.backend.Models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "branches")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "major", referencedColumnName = "major_id", nullable = false)
    private Major major;

    @Column(name = "branch_name_th", length = 100, nullable = false)
    private String nameTh;

    @Column(name = "branch_name_en", length = 100, nullable = false)
    private String nameEn;

    @Column(name = "branch_tuition_fees", precision = 10, scale = 2)
    private BigDecimal tuitionFees;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch_language", length = 30, nullable = false)
    private CourseLanguage language;

    @Builder.Default
    @OneToMany(mappedBy = "branches", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BranchCourse> courses = new HashSet<>();

    @Column(name = "branch_image", columnDefinition = "TEXT", nullable = false)
    private String image;

    @Column(name = "branch_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "branch_careers", columnDefinition = "TEXT", nullable = false)
    private String careers;

    @Column(name = "branch_status", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean status;

    public Branch() {
    }

    public Branch(Long id, Major major, String nameTh, String nameEn, BigDecimal tuitionFees, CourseLanguage language,
            Set<BranchCourse> courses, String image, String description, String careers, boolean status) {
        this.id = id;
        this.major = major;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.tuitionFees = tuitionFees;
        this.language = language;
        this.courses = courses;
        this.image = image;
        this.description = description;
        this.careers = careers;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
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

    public Set<BranchCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<BranchCourse> courses) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
