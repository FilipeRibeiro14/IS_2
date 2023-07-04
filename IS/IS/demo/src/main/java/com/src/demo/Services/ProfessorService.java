package com.src.demo.Services;

import java.util.Collections;

import org.springframework.data.domain.Sort;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.demo.Entities.Professor;
import com.src.demo.Repositories.ProfessorRepository;


import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
//@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProfessorService {

   private final ProfessorRepository professorRepository;

     
    public Mono<Professor> createProfessor(Professor professor){
        return professorRepository.save(professor);
    }

    //vai buscar todos os professores
    public Flux<Professor> getAllProfessors(){
        return professorRepository.findAll();
    }

    //econtrar professor especifico
    public Mono<Professor> findById(int id){
        return professorRepository.findById(id);
    }

    

    //update professor
    public Mono<Professor> updateProfessor(int id, Professor professor){
        return professorRepository.findById(id)
                                        .flatMap(dbProfessor -> {
                                            dbProfessor.setName(professor.getName());
                                            return professorRepository.save(dbProfessor);
                                        });
    }

    //delete professor
    public Mono<Professor> deleteProfessor(int id){
        return professorRepository.findById(id)
                                        .flatMap(existingProfessor -> professorRepository.delete(existingProfessor)
                                                .then(Mono.just(existingProfessor)));
    }   

}
