package com.src.demo.Repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.src.demo.Entities.Relacionamento;

@Repository
public interface RelacionamentoRepository extends R2dbcRepository<Relacionamento, Integer>  {
    
}
