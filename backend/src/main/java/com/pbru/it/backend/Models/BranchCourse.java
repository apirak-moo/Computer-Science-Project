package com.pbru.it.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "branch_courses")
public class BranchCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_course_id")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "branch_id", nullable = false)
    private Branch branches;

    @Column(name = "branch_course_code", length = 20, nullable = false)
    private String code;

    @Column(name = "branch_course_year", length = 2)
    private int year;

    @Column(name = "branch_course_sector", length = 2)
    private int sector;

    @Column(name = "branch_course_name_th", length = 100)
    private String nameTh;

    @Column(name = "branch_course_name_en", length = 100)
    private String nameEn;

    @Column(name = "branch_course_credit", length = 2)
    private int credit;

    public BranchCourse() {
    }

    public BranchCourse(Long id, Branch branches, String code, int year, int sector, String nameTh, String nameEn,
            int credit) {
        this.id = id;
        this.branches = branches;
        this.code = code;
        this.year = year;
        this.sector = sector;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Branch getBranches() {
        return branches;
    }

    public void setBranches(Branch branches) {
        this.branches = branches;
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

}
