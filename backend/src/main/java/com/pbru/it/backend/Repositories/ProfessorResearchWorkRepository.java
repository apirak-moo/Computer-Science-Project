package com.pbru.it.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbru.it.backend.Models.ProfessorResearchWork;

@Repository
public interface ProfessorResearchWorkRepository extends JpaRepository<ProfessorResearchWork, Long> {
    
}
