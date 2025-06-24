package com.pbru.it.backend.Repositories;

import com.pbru.it.backend.Models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>, QuerydslPredicateExecutor<Branch> {

}
