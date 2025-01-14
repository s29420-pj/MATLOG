package pl.pjatk.MATLOG.userManagement.studentUser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentUserServiceIT {

    @Autowired
    private StudentUserService studentUserService;

    @Autowired
    private StudentUserRepository studentUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Container
    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.hikari.connection-timeout", () -> "300");
    }

    @BeforeAll
    static void startUp() {
        postgres.start();
    }

    @AfterAll
    static void stop() {
        postgres.close();
    }

    @Test
    void shouldRegisterNewUser() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.STUDENT
        );

        studentUserService.registerUser(userDTO);

        assertThat(studentUserRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldNotRegisterUserWithDuplicateEmail() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.STUDENT
        );

        studentUserService.registerUser(userDTO);

        assertThatThrownBy(() -> studentUserService.registerUser(userDTO))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void shouldRetrieveStudentProfile() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.STUDENT
        );
        studentUserService.registerUser(userDTO);
        String id = studentUserRepository.findByEmailAddress("example@example.com").get().getId();
        StudentUserProfileDTO profile = studentUserService.getStudentProfile(id);

        assertThat(profile.id()).isEqualTo(id);
    }

    @Test
    void shouldChangePassword() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.STUDENT
        );
        studentUserService.registerUser(userDTO);
        String id = studentUserRepository.findByEmailAddress("example@example.com").get().getId();

        studentUserService.changePassword(id, "newpassword123");
        StudentUser studentUser = studentUserService.getStudentUserById(id);

        assertThat(passwordEncoder.matches("newpassword123", studentUser.getPassword())).isTrue();
    }

    @Test
    void shouldThrowUserNotFoundException() {
        assertThatThrownBy(() -> studentUserService.getStudentProfile("99"))
                .isInstanceOf(UserNotFoundException.class);
    }
}
