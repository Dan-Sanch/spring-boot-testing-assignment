package net.javaguides.springboottestingassignment.repository;

import net.javaguides.springboottestingassignment.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTests {
    @Autowired
    private StudentRepository studentRepository;

    @DisplayName("Create Student")
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudent() {
        // given - precondition or setup
        Student student = Student.builder()
                .firstName("Dan")
                .lastName("Sanchez")
                .email("")
                .build();

        // when - action or the behavior that we are going to test
        studentRepository.save(student);

        // then - verify the output
        assertThat(student).isNotNull();
        assertThat(student.getId()).isGreaterThan(0);
    }
}
