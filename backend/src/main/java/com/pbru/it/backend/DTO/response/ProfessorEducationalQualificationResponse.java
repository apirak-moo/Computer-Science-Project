package com.pbru.it.backend.DTO.response;

public class ProfessorEducationalQualificationResponse {
    private Long id;
    private String university;
    private String major;
    private DegreeResponse degree;
    public ProfessorEducationalQualificationResponse() {
    }
    public ProfessorEducationalQualificationResponse(Long id, String university, String major, DegreeResponse degree) {
        this.id = id;
        this.university = university;
        this.major = major;
        this.degree = degree;
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
    public DegreeResponse getDegree() {
        return degree;
    }
    public void setDegree(DegreeResponse degree) {
        this.degree = degree;
    }
}
