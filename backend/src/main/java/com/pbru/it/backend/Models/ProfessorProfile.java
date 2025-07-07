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

}
