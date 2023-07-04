package com.src.demo.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.demo.Entities.Relacionamento;
import com.src.demo.Repositories.RelacionamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RelacionamentoService{
    private final RelacionamentoRepository relationRepository;


    public Flux<Relacionamento> getAllRelations(){
        return relationRepository.findAll();
    }
    public Mono<Relacionamento> findById(int id){
        return relationRepository.findById(id);
    }

    public  Mono<Relacionamento> createRelation(Relacionamento relation){
        return relationRepository.save(relation);
    }

    public Mono<Relacionamento> deleteRelation(int relationID){
        return relationRepository.findById(relationID)
                        .flatMap(existingRelation -> relationRepository.delete(existingRelation)
                            .then(Mono.just(existingRelation)));

    }

    public Mono<Relacionamento> readRelation(int id){
        return relationRepository.findById(id);
    }


}
