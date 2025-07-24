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

@Table(name = "professor_educational_qualification")
@Entity
public class ProfessorEducationalQualification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_educational_qualification_id")
    private Long id;

    @Column(name = "professor_educational_qualification_university", length = 150, nullable = false)
    private String university;

    @Column(name = "professor_educational_qualification_major", length = 150, nullable = false)
    private String major;

    @ManyToOne
    @JoinColumn(name = "degree_id", referencedColumnName = "degree_id", nullable = false)
    private Degree degree;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "professor_id", nullable = false)
    private Professor professor;

    public ProfessorEducationalQualification() {
    }

    public ProfessorEducationalQualification(Long id, String university, String major, Degree degree,
            Professor professor) {
        this.id = id;
        this.university = university;
        this.major = major;
        this.degree = degree;
        this.professor = professor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}
