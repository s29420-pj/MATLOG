package pl.pjatk.MATLOG.userManagement.studentUser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StudentUserControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentUserService studentUserService;

    private static final String baseUrl = "/student/user/controller";

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

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", dto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Student user testEmailAddress@example.com has been registered");
    }

    @Test
    void shouldReturnBadRequestWhenRoleIsNotStudent() throws Exception {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Tried to create TUTOR as StudentUser");
    }

    @Test
    void shouldReturnStudentProfileSuccessfully() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO("testFirstName",
                "testLastName", "testEmailAddress@example.com",
                "P@ssword!", LocalDate.now().minusYears(20),
                Role.STUDENT);

        restTemplate.postForEntity(baseUrl + "/register", dto, String.class);

        var userId = studentUserService.getStudentProfileByEmailAddress(dto.emailAddress()).id();

        ResponseEntity<StudentUserProfileDTO> response =
                restTemplate.getForEntity(baseUrl + "/get/profile/" + userId, StudentUserProfileDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldChangePasswordSuccessfully() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO("testFirstName",
                "testLastName", "testEmailAddress@example.com",
                "P@ssword!", LocalDate.now().minusYears(20),
                Role.STUDENT);

        restTemplate.postForEntity(baseUrl + "/register", dto, String.class);

        String rawPassword = "newPassword123";

        var userId = studentUserService.getStudentProfileByEmailAddress(dto.emailAddress()).id();

        ResponseEntity<Void> response = restTemplate.withBasicAuth(dto.emailAddress(), dto.password())
                .exchange(baseUrl + "/change/password/" + userId,
                        HttpMethod.PUT,
                        new HttpEntity<>(rawPassword),
                        Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
