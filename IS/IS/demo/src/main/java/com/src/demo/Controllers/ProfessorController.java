package com.src.demo.Controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.src.demo.Entities.Professor;

import com.src.demo.Services.ProfessorService;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/professor")
@RequiredArgsConstructor
@Slf4j
public class ProfessorController {

  private final ProfessorService profService;


  
  @GetMapping("/all")
  public Flux<Professor> getAll() {
    return profService.getAllProfessors();
  }

  @GetMapping(value = "/find/{id}")
  public Mono<Professor> findById(@PathVariable int id) {
    return profService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Professor> createProfessor(@RequestBody Professor professor) {
    return profService.createProfessor(professor);
  }

  @PutMapping("/update/{id}")
  public Mono<Professor> updateProfessor(@PathVariable int id, @RequestBody Professor prof) {
    return profService.updateProfessor(id,prof);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteProfessor(@PathVariable int id) {
    return profService.deleteProfessor(id)
                      .map(r -> ResponseEntity.ok().<Void>build())
                      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}