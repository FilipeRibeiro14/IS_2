package com.src.demo.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.src.demo.Entities.Relacionamento;
import com.src.demo.Services.ProfessorService;
import com.src.demo.Services.RelacionamentoService;
import com.src.demo.Services.StudentService;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/relacionamento")
@RequiredArgsConstructor
@Slf4j
public class RelacionamentoController {
    private final RelacionamentoService relacionamentoService;
   


  
  @GetMapping("/all")
  public Flux<Relacionamento> getAllRelations() {
    return relacionamentoService.getAllRelations();
  }

  @GetMapping(value = "/find/{id}")
  public Mono<Relacionamento> findById(@PathVariable int id) {
    return relacionamentoService.findById(id);
  }

  @PostMapping(value = "/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Relacionamento> createRelation(@RequestBody Relacionamento r) {
    return relacionamentoService.createRelation(r);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteRelation(@PathVariable int id) {
    return relacionamentoService.deleteRelation(id)
                      .map(r -> ResponseEntity.ok().<Void>build())
                      .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
