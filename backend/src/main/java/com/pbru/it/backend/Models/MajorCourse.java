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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "major_courses")
public class MajorCourse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_course_id")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "major", referencedColumnName = "major_id", nullable = false)
    private Major major;

    @Column(name = "major_course_code", length = 20, nullable = false)
    private String code;

    @Column(name = "major_course_year", length = 2)
    private int year;

    @Column(name = "major_course_sector", length = 2)
    private int sector;

    @Column(name = "major_course_name_th", length = 100)
    private String nameTh;

    @Column(name = "major_course_name_en", length = 100)
    private String nameEn;

    @Column(name = "major_course_credit", length = 2)
    private int credit;

}
