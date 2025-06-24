package com.pbru.it.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbru.it.backend.Models.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Integer> {
    
}
