package com.pbru.it.backend.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @Entity
// @Table(name = "specializations")
public class Specialization {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "specialization_id")
    private int id;

    // @Column(name = "specialization_name_th", length = 100, nullable = false)
    private String nameTh;
    
    // @Column(name = "specialization_name_en", length = 100, nullable = false)
    private String nameEn;

    // @Column(name = "specialization_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    // @Column(name = "specialization_status")
    private Boolean status;

}
