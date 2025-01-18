package pl.pjatk.MATLOG.userManagement.studentUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StudentUserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentUserService studentUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void dynamicProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.datasource.hikari.connection-timeout", () -> 3000);
    }

    @BeforeAll
    static void startUp() {
        postgres.start();
    }

    @AfterAll
    static void close() {
        postgres.close();
    }

    @Test
    void shouldRegisterStudentUserSuccessfully() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO("testFirstName",
                "testLastName", "testEmailAddress@example.com",
                "P@ssword!", LocalDate.now().minusYears(20),
                Role.STUDENT);

        mockMvc.perform(post("/student/user/controller/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Student user testEmailAddress@example.com has been registered"));
    }

    @Test
    void shouldReturnBadRequestWhenRoleIsNotStudent() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO("testFirstName",
                "testLastName", "testEmailAddress@example.com",
                "P@ssword!", LocalDate.now().minusYears(20),
                Role.TUTOR);

        mockMvc.perform(post("/student/user/controller/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Tried to create TUTOR as StudentUser"));
    }

    @Test
    void shouldReturnStudentProfileSuccessfully() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO("testFirstName",
                "testLastName", "testEmailAddress@example.com",
                "P@ssword!", LocalDate.now().minusYears(20),
                Role.STUDENT);

        mockMvc.perform(post("/student/user/controller/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        var userId = studentUserService.getStudentProfileByEmailAddress(dto.emailAddress());

        mockMvc.perform(get("/student/user/controller/get/profile/", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId));
    }

    @Test
    void shouldChangePasswordSuccessfully() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO("testFirstName",
                "testLastName", "testEmailAddress@example.com",
                "P@ssword!", LocalDate.now().minusYears(20),
                Role.STUDENT);

        mockMvc.perform(post("/student/user/controller/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        String rawPassword = "newpassword123";

        var userId = studentUserService.getStudentProfileByEmailAddress(dto.emailAddress()).id();

        mockMvc.perform(put("/student/user/controller/change/password/{id}", userId)
                        .param("rawPassword", rawPassword))
                .andExpect(status().isAccepted());
    }
}
