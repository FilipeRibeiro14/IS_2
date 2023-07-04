package com.src.demo.Controllers;

import com.src.demo.Entities.Student;
import com.src.demo.Services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

  private final StudentService studentService;
  Logger logger;

  @GetMapping("/all")
  public Flux<Student> getAll() {
    Logger.getLogger("Pedido feito para enviar todos os estudantes\n");
    return studentService.getAllStudents();
  }

  @GetMapping(value = "/find/{id}")
  public Mono<Student> findById(@PathVariable int id) {
    return studentService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Student> createStudent(@RequestBody Student student) {
    return studentService.createStudent(student);
  }

  

  @PutMapping("/update/{id}")
  public Mono<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
    return studentService.updateStudent(id, student);
        
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable int id) {
    return studentService.deleteStudent(id)
                                .map(r -> ResponseEntity.ok().<Void>build())
                                .defaultIfEmpty(ResponseEntity.notFound().build());
  }



}