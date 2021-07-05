package com.inspo.solutions.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inspo.solutions.model.Student;

@Controller
@RequestMapping("/api/v1/student")
public class StudentApi {
	
	private static List<Student> studentList = new ArrayList<>();
	static {
		Student student0 = new Student.StudentBuilder().setId(1).setFirstName("Abhik").setLastName("Talukder").setCgpa(3.96).build();
		Student student1 = new Student.StudentBuilder().setId(2).setFirstName("Jaysen").setLastName("Talukder").setCgpa(3.97).build();
		Student student2 = new Student.StudentBuilder().setId(3).setFirstName("Arithra").setLastName("Talukder").setCgpa(3.94).build();
		Student student3 = new Student.StudentBuilder().setId(4).setFirstName("Ria").setLastName("Talukder").setCgpa(3.98).build();
		Student student4 = new Student.StudentBuilder().setId(5).setFirstName("Arnab").setLastName("Talukder").setCgpa(3.99).build();
		studentList.add(student0);
		studentList.add(student1);
		studentList.add(student2);
		studentList.add(student3);
		studentList.add(student4);
	}
	
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	
	public ResponseEntity<Student> getStudentById(@PathVariable("studentId") int studentId) {
		return new ResponseEntity<Student>(studentList.get(studentId), HttpStatus.OK);
	}

}
