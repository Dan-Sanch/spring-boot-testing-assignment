package net.javaguides.springboottestingassignment.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name="Student")
@Builder
public class Student {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
}
