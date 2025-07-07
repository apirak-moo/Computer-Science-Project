package com.pbru.it.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbru.it.backend.Models.ProfessorTitle;

@Repository
public interface ProfessorTitleRepository extends JpaRepository<ProfessorTitle, Integer> {

}
