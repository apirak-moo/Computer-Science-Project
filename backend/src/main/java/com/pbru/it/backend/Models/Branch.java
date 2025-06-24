package com.pbru.it.backend.Models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "branches")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    @EqualsAndHashCode.Include
    private Long id;


    @ManyToOne
    @JoinColumn(name = "major", referencedColumnName = "major_id", nullable = false)
    private Major major;

    @Column(name = "branch_name_th", length = 100, nullable = false)
    private String nameTh;

    @Column(name = "branch_name_en", length = 100, nullable = false)
    private String nameEn;

    @Column(name = "branch_tuition_fees", precision = 10, scale = 2)
    private BigDecimal tuitionFees;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch_language", length = 30, nullable = false)
    private CourseLanguage language;

    @Builder.Default
    @OneToMany(mappedBy = "branches", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BranchCourse> courses = new HashSet<>();

    @Column(name = "branch_image", columnDefinition = "TEXT", nullable = false)
    private String image;

    @Column(name = "branch_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "branch_careers", columnDefinition = "TEXT", nullable = false)
    private String careers;

    @Column(name = "branch_status", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean status;

}
