package com.pbru.it.backend.Models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "professor_profiles")
public class ProfessorProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_profile_id")
    private Long id;

    @Column(name = "professor_profile_nameTh", length = 100, nullable = false)
    private String nameTh;

    @Column(name = "professor_profile_nameEn", length = 100, nullable = false)
    private String nameEn;

    @Column(name = "professor_profile_birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "professor_profile_image", nullable = false)
    private String image;

    @Column(name = "professor_major", nullable = false, length = 100)
    private String major;

    @OneToOne
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private Professor professor;

}
