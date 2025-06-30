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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "professor_educational_qualification")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorEducationalQualification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_educational_qualification_id")
    private Long id;

    @Column(name = "professor_educational_qualification_university", length = 150, nullable = false)
    private String university;

    @Column(name = "professor_educational_qualification_major", length = 150, nullable = false)
    private String major;

    @Column(name = "professor_educational_qualification_year_of_graduation", length = 4, nullable = false)
    private String yearGraduation;

    @ManyToOne
    @JoinColumn(name = "degree_id", referencedColumnName = "degree_id", nullable = false)
    private Degree degree;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "professor_id", nullable = false)
    private Professor professor;

}
