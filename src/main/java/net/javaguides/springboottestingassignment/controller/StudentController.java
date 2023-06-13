package net.javaguides.springboottestingassignment.controller;

import net.javaguides.springboottestingassignment.entity.Student;
import net.javaguides.springboottestingassignment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student saveStudent) {
        Student createdStudent = studentService.createStudent(saveStudent);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
