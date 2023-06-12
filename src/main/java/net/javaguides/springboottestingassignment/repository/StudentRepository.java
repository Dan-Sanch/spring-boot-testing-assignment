package net.javaguides.springboottestingassignment.repository;


import net.javaguides.springboottestingassignment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
