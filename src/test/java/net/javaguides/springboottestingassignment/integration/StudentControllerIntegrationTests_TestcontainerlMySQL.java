package net.javaguides.springboottestingassignment.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboottestingassignment.entity.Student;
import net.javaguides.springboottestingassignment.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
// These test will use as a DB whatever the application is configured to use. See application.properties
public class StudentControllerIntegrationTests_TestcontainerlMySQL {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentRepository studentRepository;
    @Container
    private static MySQLContainer MY_SQL_CONTAINER;
    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
                .withDatabaseName("foo")
                .withUsername("foo")
                .withPassword("secret");
        MY_SQL_CONTAINER.start();
    }
    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }

    private Student student;
    @BeforeEach
    void buildBaseStudent() {
        studentRepository.deleteAll();
        student = Student.builder()
                .firstName("Dan")
                .lastName("Sanchez")
                .email("dan@domain.com")
                .build();
    }

    @Test
    @DisplayName("POST create student test")
    public void givenStudentObject_whenCreateStudent_thenReturnSavedStudent() throws Exception {
        // Given

        // When
        ResultActions response = mockMvc.perform( // <-- throws exception
                MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)) // <-- throws exception
        );

        // Then
        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        CoreMatchers.is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        CoreMatchers.is(student.getLastName())))
                .andExpect(jsonPath("$.email",
                        CoreMatchers.is(student.getEmail())))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("Get all students test")
    public void givenListOfStudents_whenGetAllStudents_thenReturnListOfStudents() throws Exception {
        // Given
        Student student2 = Student.builder()
                .firstName("Dan2")
                .lastName("Sanchez2")
                .email("dan2@domain.com")
                .build();
        List<Student> listOfStudents = List.of(student, student2);
        studentRepository.saveAll(listOfStudents);

        // When
        ResultActions response = mockMvc.perform( // <-- throws exception
                MockMvcRequestBuilders.get("/api/students")
        );

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", // Test response data size
                        CoreMatchers.is(listOfStudents.size())))
                .andExpect(jsonPath("$[0].firstName",
                        CoreMatchers.is(student.getFirstName())))
                .andDo(print())
        ;
    }
}
