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

@Table(name = "professor_research_work_images")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfessorResearchWorkImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_research_work_image_id")
    private Long id;

    @Column(name = "professor_research_work_image_path", columnDefinition = "TEXT", nullable = false)
    private String path;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_research_work_id", referencedColumnName = "professor_research_work_id")
    private ProfessorResearchWork work;

}
