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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professor_expertises")
@Entity
public class ProfessorExpertise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_expertise_id")
    private Long id;

    @Column(name = "professor_expertise_name", length = 100)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_profile_id", referencedColumnName = "professor_profile_id")
    private ProfessorProfile profile;

}
