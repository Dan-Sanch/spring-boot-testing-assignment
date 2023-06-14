package net.javaguides.springboottestingassignment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Student")
@Builder
@NoArgsConstructor // Required by Data JPA
@AllArgsConstructor // Required by @Builder
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column
    private String email;
}
