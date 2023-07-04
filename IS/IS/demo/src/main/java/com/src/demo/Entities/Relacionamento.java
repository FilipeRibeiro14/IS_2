package com.src.demo.Entities;



import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@Table(value = "relacionamento")
public class Relacionamento {
    
    @Id
    int id;
    int student_id;
    int professor_id;

    /* 
    public Relacionamento(int studentId,int professorId){
        this.studentId = studentId;
        this.professorId = professorId;
    }
   
   */
    
   
}
