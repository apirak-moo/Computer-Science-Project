package com.pbru.it.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "professor_profiles")
public class ProfessorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_profile_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_title_id", referencedColumnName = "professor_title_id", nullable = false)
    private ProfessorTitle title;

    @Column(name = "professor_profile_name", length = 100, nullable = false)
    private String name;

    @Column(name = "professor_profile_phone", length = 12, nullable = false)
    private String phone;

    @Column(name = "professor_profile_git", columnDefinition = "TEXT", nullable = false)
    private String git;

    @Column(name = "professor_profile_image", nullable = false)
    private String image;

    @Column(name = "professor_major", nullable = false, length = 100)
    private String major;

    @OneToOne
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private Professor professor;

    public  ProfessorProfile(){

    }

    public ProfessorProfile(Long id, ProfessorTitle title, String name, String phone, String git, String image, String major, Professor professor) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.phone = phone;
        this.git = git;
        this.image = image;
        this.major = major;
        this.professor = professor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfessorTitle getTitle() {
        return title;
    }

    public void setTitle(ProfessorTitle title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}
