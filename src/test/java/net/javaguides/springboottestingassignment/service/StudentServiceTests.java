package net.javaguides.springboottestingassignment.service;

import net.javaguides.springboottestingassignment.entity.Student;
import net.javaguides.springboottestingassignment.repository.StudentRepository;
import net.javaguides.springboottestingassignment.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    public void buildBaseStudent() {
        student = Student.builder()
                .id(1L)
                .firstName("Dan")
                .lastName("Sanchez")
                .email("dan@domain.com")
                .build();
    }

    @DisplayName("Create Student test")
    @Test
    public void givenStudentObject_whenCreateStudent_thenReturnStudent() {
        // given - precondition or setup
        given(studentRepository.save(student))
                .willReturn(student);

        // when - action or the behavior that we are going to test
        Student savedStudent = studentService.createStudent(student);

        // then - verify the output
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent).isEqualTo(student);
    }

    @DisplayName("Create Student test")
    @Test
    public void givenListOfStudents_whenGetAllStudents_thenReturnStudent() {
        // given - precondition or setup
        given(studentRepository.save(student))
                .willReturn(student);

        // when - action or the behavior that we are going to test
        Student savedStudent = studentService.createStudent(student);

        // then - verify the output
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent).isEqualTo(student);
    }

    @DisplayName("Get all students test")
    @Test
    public void givenListOfStudents_whenGetAllStudents_thenListOfStudents() {
        // given - precondition or setup
        Student student2 = Student.builder()
                .id(1L)
                .firstName("Dan")
                .lastName("Sanchez")
                .email("dan@domain.com")
                .build();
        List<Student> listOfStudents = List.of(student, student2);
        given(studentRepository.findAll())
                .willReturn(listOfStudents);

        // when - action or the behavior that we are going to test
        List<Student> foundStudents = studentService.getAllStudents();

        // then - verify the output
        assertThat(foundStudents).isNotNull();
        assertThat(foundStudents.size()).isEqualTo(2);
        assertThat(foundStudents).contains(student, student2);
    }
}
