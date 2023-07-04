package com.src.demo.Repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.src.demo.Entities.Professor;

@Repository
public interface ProfessorRepository extends R2dbcRepository<Professor, Integer> {
    
}