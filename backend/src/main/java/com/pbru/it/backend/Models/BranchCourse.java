package com.pbru.it.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "branch_courses")
public class BranchCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_course_id")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "branch_id", nullable = false)
    private Branch branches;

    @Column(name = "branch_course_code", length = 20, nullable = false)
    private String code;

    @Column(name = "branch_course_year", length = 2)
    private int year;

    @Column(name = "branch_course_sector", length = 2)
    private int sector;

    @Column(name = "branch_course_name_th", length = 100)
    private String nameTh;

    @Column(name = "branch_course_name_en", length = 100)
    private String nameEn;

    @Column(name = "branch_course_credit", length = 2)
    private int credit;

}
