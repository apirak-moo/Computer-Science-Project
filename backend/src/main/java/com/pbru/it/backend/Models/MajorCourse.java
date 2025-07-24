package com.pbru.it.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@Entity
@Table(name = "major_courses")
public class MajorCourse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_course_id")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "major", referencedColumnName = "major_id", nullable = false)
    private Major major;

    @Column(name = "major_course_code", length = 20, nullable = false)
    private String code;

    @Column(name = "major_course_year", length = 2)
    private int year;

    @Column(name = "major_course_sector", length = 2)
    private int sector;

    @Column(name = "major_course_name_th", length = 100)
    private String nameTh;

    @Column(name = "major_course_name_en", length = 100)
    private String nameEn;

    @Column(name = "major_course_credit", length = 2)
    private int credit;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public MajorCourse(Long id, Major major, String code, int year, int sector, String nameTh, String nameEn,
            int credit) {
        this.id = id;
        this.major = major;
        this.code = code;
        this.year = year;
        this.sector = sector;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.credit = credit;
    }

    public MajorCourse() {
    }

}
