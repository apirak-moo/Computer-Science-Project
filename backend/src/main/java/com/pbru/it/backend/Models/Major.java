package com.pbru.it.backend.Models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "majors")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program", referencedColumnName = "program_id", nullable = false)
    private Program program;

    @Column(name = "major_name_th", length = 100, nullable = false)
    private String nameTh;

    @Column(name = "major_name_en", length = 100, nullable = false)
    private String nameEn;

    @Column(name = "major_tuition_fees", precision = 10, scale = 2)
    private BigDecimal tuitionFees;

    @Enumerated(EnumType.STRING)
    @Column(name = "major_language", length = 30, nullable = false)
    private CourseLanguage language;

    @Builder.Default
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MajorCourse> courses = new HashSet<>();

    @Column(name = "major_image", columnDefinition = "TEXT", nullable = false)
    private String image;

    @Column(name = "major_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "major_careers", columnDefinition = "TEXT", nullable = false)
    private String careers;

    @Column(name = "major_status", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean status;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Branch> branches = new HashSet<>();

}
