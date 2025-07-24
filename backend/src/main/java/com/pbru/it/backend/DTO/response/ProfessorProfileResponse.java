package com.pbru.it.backend.DTO.response;

public class ProfessorProfileResponse {
    
    private Long id;
    private ProfessorTitleResponse title;
    private String name;
    private String phone;
    private String git;
    private String major;
    private String image;

    public ProfessorProfileResponse() {
    }
    public ProfessorProfileResponse(Long id, ProfessorTitleResponse title, String name, String phone, String git,
            String major, String image) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.phone = phone;
        this.git = git;
        this.major = major;
        this.image = image;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ProfessorTitleResponse getTitle() {
        return title;
    }
    public void setTitle(ProfessorTitleResponse title) {
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
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
} 
