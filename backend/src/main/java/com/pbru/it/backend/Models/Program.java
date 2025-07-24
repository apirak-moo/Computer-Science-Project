package com.pbru.it.backend.Models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    @Column(name = "program_name_th", length = 100, nullable = false)
    private String nameTh;

    @Column(name = "program_name_en", length = 100, nullable = false)
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "degree", referencedColumnName = "degree_id", nullable = false)
    private Degree degree;

    @JsonIgnore
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private Set<Major> majors;

    public Program() {
    }

    public Program(Long id, String nameTh, String nameEn, Degree degree, Set<Major> majors) {
        this.id = id;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.degree = degree;
        this.majors = majors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Set<Major> getMajors() {
        return majors;
    }

    public void setMajors(Set<Major> majors) {
        this.majors = majors;
    }

}
