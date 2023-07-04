package com.src.demo.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.demo.Entities.Relacionamento;
import com.src.demo.Entities.Student;
import com.src.demo.Repositories.RelacionamentoRepository;
import com.src.demo.Repositories.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final RelacionamentoRepository relacionamentoRepository;
    
    public Mono<Student> createStudent(Student student){
        return studentRepository.save(student);
    }

    //vai buscar todos os estudantes
    public Flux<Student> getAllStudents(){
        return studentRepository.findAll();
        
    }

    public Mono<Student> findById(int id){
        return studentRepository.findById(id);

    }
    
    public Mono<Student> updateStudent(int id, Student student){
        return studentRepository.findById(id)
                                .flatMap(dbStudent -> {
                                                        dbStudent.setName(student.getName());
                                                        dbStudent.setBirth(student.getBirth());
                                                        dbStudent.setCredits(student.getCredits());
                                                        dbStudent.setAvg_grade(student.getAvg_grade());
                                                        return studentRepository.save(dbStudent); 
                                                    });

    }

    public Mono<Student> deleteStudent(int id){
        /*relacionamentoRepository.findAll()
        .filter(r -> r.getStudent_id() == id)
        .hasElements()
        .subscribe(b -> getBoolean(b, id));*/
         
        return studentRepository.findById(id)
                        .flatMap(existingStudent -> studentRepository.delete(existingStudent)
                            .then(Mono.just(existingStudent)));

    }
/* 
    public Mono<Student> checkRelation(int id){
        relacionamentoRepository.findAll()
        .filter(r -> r.getStudent_id() == id)
        .hasElements()
        .subscribe(b -> getBoolean(b, id));

        studentRepository.findById(id);


    }
*/


}
