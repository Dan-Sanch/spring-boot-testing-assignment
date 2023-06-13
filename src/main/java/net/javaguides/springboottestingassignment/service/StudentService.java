package net.javaguides.springboottestingassignment.service;

import net.javaguides.springboottestingassignment.entity.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    List<Student> getAllStudents();
}
