package com.pbru.it.backend.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbru.it.backend.Models.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, UUID> {
    Optional<Professor> findByEmail(String email);
    boolean existsByEmail(String email);
}
