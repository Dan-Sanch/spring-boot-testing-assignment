package net.javaguides.springboottestingassignment.repository;

import net.javaguides.springboottestingassignment.entity.Student;
import net.javaguides.springboottestingassignment.integration.TestcontainersMySqlAbstraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryIntegrationTests extends TestcontainersMySqlAbstraction {
    @Autowired
    private StudentRepository studentRepository;
    private Student student;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        student = Student.builder()
                .firstName("Dan")
                .lastName("Sanchez")
                .email("dan@email.com")
                .build();
    }

    @DisplayName("Create Student")
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudent() {
        // given - precondition or setup

        // when - action or the behavior that we are going to test
        Student savedStudent = studentRepository.save(student);

        // then - verify the output
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    @DisplayName("Get all students test")
    @Test
    public void givenStudentsList_whenFindAll_thenReturnAllStudents() {
        // given - precondition or setup
        Student student2 = Student.builder()
                .firstName("Dan2")
                .lastName("Sanchez2")
                .email("dan2@email.com")
                .build();
        studentRepository.saveAll(List.of(student, student2));

        // when - action or the behavior that we are going to test
        List<Student> savedStudents = studentRepository.findAll();

        // then - verify the output
        assertThat(savedStudents).isNotNull();
        assertThat(savedStudents.size()).isEqualTo(2);
        assertThat(savedStudents).contains(student, student2);
    }

    @DisplayName("Get student by ID")
    @Test
    public void givenStudentObject_whenFindById_thenReturnStudentObject() {
        // given - precondition or setup
        Student savedStudent = studentRepository.save(student);

        // when - action or the behavior that we are going to test
        Optional<Student> optionalStudent = studentRepository.findById(savedStudent.getId());

        // then - verify the output
        assertThat(optionalStudent).isPresent();
        assertThat(optionalStudent.get().getId()).isEqualTo(savedStudent.getId());
        assertThat(optionalStudent.get()).isEqualTo(savedStudent);
    }

    @DisplayName("Get student by email")
    @Test
    public void givenStudentEmail_whenFindByEmail_thenReturnStudentObject() {
        // given - precondition or setup
        Student savedStudent = studentRepository.save(student);

        // when - action or the behavior that we are going to test
        Optional<Student> optionalStudent = studentRepository.findByEmail(savedStudent.getEmail());

        // then - verify the output
        assertThat(optionalStudent).isPresent();
        assertThat(optionalStudent.get().getId()).isEqualTo(savedStudent.getId());
    }

    @DisplayName("Update Student Name")
    @Test
    public void givenStudentObject_whenUpdateStudent_thenReturnUpdatedStudent() {
        // given - precondition or setup
        studentRepository.save(student);

        // when - action or the behavior that we are going to test
        Student savedStudent = studentRepository.findById(student.getId()).get();
        savedStudent.setFirstName("DanUpdated");
        Student updatedStudent = studentRepository.save(savedStudent);

        // then - verify the output
        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getId()).isEqualTo(savedStudent.getId());
        assertThat(updatedStudent.getFirstName()).isEqualTo("DanUpdated");
    }

    @DisplayName("Delete Student")
    @Test
    public void givenStudentObject_whenDeleteStudent_thenRemoveStudent() {
        // given - precondition or setup
        studentRepository.save(student);

        // when - action or the behavior that we are going to test
        studentRepository.delete(student);
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());

        // then - verify the output
        assertThat(optionalStudent).isEmpty();
    }

    @DisplayName("Delete Student by ID")
    @Test
    public void givenStudentObject_whenDeleteStudentById_thenRemoveStudent() {
        // given - precondition or setup
        studentRepository.save(student);

        // when - action or the behavior that we are going to test
        studentRepository.deleteById(student.getId());
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());

        // then - verify the output
        assertThat(optionalStudent).isEmpty();
    }
}
