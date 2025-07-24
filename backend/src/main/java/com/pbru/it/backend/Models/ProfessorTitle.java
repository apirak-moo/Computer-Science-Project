package com.pbru.it.backend.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "professor_title")
@Entity
public class ProfessorTitle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_title_id")
    private int id;

    @Column(name = "professor_title_name", length = 100, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfessorProfile> profiles = new ArrayList<>();

    public ProfessorTitle() {
    }

    public ProfessorTitle(int id, String name, List<ProfessorProfile> profiles) {
        this.id = id;
        this.name = name;
        this.profiles = profiles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProfessorProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfessorProfile> profiles) {
        this.profiles = profiles;
    }

}
