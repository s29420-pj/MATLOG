package pl.pjatk.MATLOG.userManagement.tutorUser;

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
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.StudentUserService;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TutorUserControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TutorUserService tutorUserService;

    @Autowired
    private StudentUserService studentUserService;

    private static final String baseUrl = "/tutor/user/controller";

    @Container
    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void registry(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.datasource.hikari.connection-timeout", () -> 300);
    }

    @BeforeAll
    static void setUp() {
        postgres.start();
    }

    @AfterAll
    static void close() {
        postgres.close();
    }

    @Test
    void shouldRegisterTutorUser() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Tutor user testEmailAddress@example.com has been registered");
    }

    @Test
    void shouldNotRegisterNonTutorUser() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.STUDENT
        );

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Tried to create STUDENT as TutorUser");
    }

    @Test
    void shouldGetTutorProfile() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);
        String tutorId = tutorUserService.getTutorUserProfileByEmailAddress(userDTO.emailAddress()).id();

        ResponseEntity<TutorUserProfileDTO> response = restTemplate.getForEntity(baseUrl + "/get/profile/" + tutorId,
                TutorUserProfileDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldReturnNotFoundForNonExistentTutorProfile() {
        String invalidTutorId = UUID.randomUUID().toString();

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/get/profile/" + invalidTutorId, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldChangePassword() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);

        String tutorId = tutorUserService.getTutorUserProfileByEmailAddress(userDTO.emailAddress()).id();

        String newPassword = "newPassword!";

        ResponseEntity<Void> response = restTemplate.withBasicAuth(userDTO.emailAddress(), userDTO.password())
                .exchange(
                baseUrl + "/change/password/" + tutorId,
                HttpMethod.PUT,
                new HttpEntity<>(newPassword),
                Void.class
        );

        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void shouldReturnBadRequestForInvalidPasswordChange() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);
        String tutorId = tutorUserService.getTutorUserProfileByEmailAddress(userDTO.emailAddress()).id();

        String invalidPassword = "";

        ResponseEntity<Void> response = restTemplate.withBasicAuth(userDTO.emailAddress(), userDTO.password())
                .exchange(
                    baseUrl + "/change/password/" + tutorId,
                    HttpMethod.PUT,
                    new HttpEntity<>(invalidPassword),
                    Void.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldAddSpecializations() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);
        String tutorId = tutorUserService.getTutorUserProfileByEmailAddress(userDTO.emailAddress()).id();

        List<SchoolSubject> specializations = List.of(SchoolSubject.MATHEMATICS);

        ResponseEntity<Void> response = restTemplate.withBasicAuth(userDTO.emailAddress(), userDTO.password())
                .exchange(
                    baseUrl + "/add/specializations/" + tutorId,
                    HttpMethod.PUT,
                    new HttpEntity<>(specializations),
                    Void.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void shouldRemoveSpecializations() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);
        String tutorId = tutorUserService.getTutorUserProfileByEmailAddress(userDTO.emailAddress()).id();

        List<SchoolSubject> specializations = List.of(SchoolSubject.MATHEMATICS);

        ResponseEntity<Void> response = restTemplate.withBasicAuth(userDTO.emailAddress(), userDTO.password())
                .exchange(
                baseUrl + "/remove/specializations/" + tutorId,
                HttpMethod.PUT,
                new HttpEntity<>(specializations),
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void shouldAddReview() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testFirstName",
                "testLastName",
                "testEmailAddress@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        );

        var studentUserDTO = new UserRegistrationDTO(
                "testFirstName2",
                "testLastName2",
                "testEmailAddress2@example.com",
                "testPassword!",
                LocalDate.now().minusYears(30),
                Role.STUDENT
        );

        restTemplate.postForEntity(baseUrl + "/register", userDTO, String.class);
        restTemplate.postForEntity("/student/user/controller/register", studentUserDTO, String.class);

        String tutorId = tutorUserService.getTutorUserProfileByEmailAddress(userDTO.emailAddress()).id();
        String studentId = studentUserService.getStudentProfileByEmailAddress(studentUserDTO.emailAddress()).id();

        ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO(
                Rate.FOUR,
                "testComment",
                LocalDateTime.now().minusHours(3),
                new StudentUserReviewLookUpDTO(studentId, "testFirstName2")
        );

        ResponseEntity<Void> response = restTemplate.withBasicAuth(studentUserDTO.emailAddress(), studentUserDTO.password())
                .postForEntity(
                baseUrl + "/add/review/" + tutorId,
                reviewCreationDTO,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
