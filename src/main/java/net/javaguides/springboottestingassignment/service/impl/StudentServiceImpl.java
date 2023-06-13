package net.javaguides.springboottestingassignment.service.impl;

import net.javaguides.springboottestingassignment.entity.Student;
import net.javaguides.springboottestingassignment.repository.StudentRepository;
import net.javaguides.springboottestingassignment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
