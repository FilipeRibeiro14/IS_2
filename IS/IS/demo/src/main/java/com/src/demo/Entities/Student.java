package com.src.demo.Entities;

import org.springframework.data.annotation.Id;

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
public class Student {
    
    @Id
    int id;
	String name; 
    String birth;
    int credits;
    float avg_grade;
    

    /*public Student(){

    }
    public Student(int id, String name, String birth, int credtis, int avg_grade){
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.credits = credits;
        this.avg_grade = avg_grade;

    }


    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getAvg_grade() {
        return avg_grade;
    }

    public void setAvg_grade(int avg_grade) {
        this.avg_grade = avg_grade;
    }*/
}
