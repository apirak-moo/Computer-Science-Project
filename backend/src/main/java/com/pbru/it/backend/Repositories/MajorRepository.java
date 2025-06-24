package com.pbru.it.backend.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.pbru.it.backend.Models.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long>, QuerydslPredicateExecutor<Major> {
    Page<Major> findByNameThContainingIgnoreCaseOrNameEnContainingIgnoreCase(String nameTh, String nameEn, Pageable pageable);
}
