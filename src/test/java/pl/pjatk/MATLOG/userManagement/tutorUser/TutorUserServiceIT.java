package pl.pjatk.MATLOG.userManagement.tutorUser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserRepository;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TutorUserServiceIT {

    @Autowired
    private TutorUserService tutorUserService;

    @Autowired
    private TutorUserRepository tutorUserRepository;

    @Autowired
    private TutorUserDAOMapper tutorUserDAOMapper;

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
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeAll
    static void setUp() {
        postgres.start();
    }

    @AfterAll
    static void powerOff() {
        postgres.close();
    }

    @Test
    void shouldRegisterNewTutorUser() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.TUTOR
        );

        tutorUserService.registerUser(userDTO);

        assertTrue(tutorUserRepository.findByEmailAddress("example@example.com").isPresent());
    }

    @Test
    void shouldThrowExceptionWhenUserDTOIsNull() {
        assertThrows(IllegalArgumentException.class, () -> tutorUserService.registerUser(null));
    }

    @Test
    void shouldNotRegisterUserIfEmailAlreadyExists() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.TUTOR
        );

        tutorUserService.registerUser(userDTO);

        assertThrows(UserAlreadyExistsException.class, () ->
                tutorUserService.registerUser(userDTO)
        );
    }

    @Test
    void shouldChangeBiography() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.TUTOR
        );

        tutorUserService.registerUser(userDTO);

        String id = tutorUserRepository.findByEmailAddress("example@example.com").get().getId();

        tutorUserService.changeBiography(id, "testBio");

        TutorUserDAO tutorUserDAO = tutorUserRepository.findByEmailAddress("example@example.com").get();

        assertAll(() -> {
            assertEquals(id, tutorUserDAO.getId());
            assertEquals("testBio", tutorUserDAO.getBiography());
        });
    }

    @Test
    void shouldChangePasswordSuccessfully() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.TUTOR
        );

        tutorUserService.registerUser(userDTO);

        TutorUserDAO tutorUserDAO = tutorUserRepository.findByEmailAddress("example@example.com").orElseThrow();
        TutorUser tutor = tutorUserDAOMapper.mapToDomain(tutorUserDAO);
        tutorUserService.changePassword(tutor.getId(), "newPassword123!");

        assertTrue(passwordEncoder.matches("newPassword123!",
                tutorUserDAOMapper.mapToDomain(
                        tutorUserRepository.findById(tutor.getId())
                                .orElseThrow()).getPassword()));
    }

    @Test
    void shouldAddSpecializationSuccessfully() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.TUTOR
        );

        tutorUserService.registerUser(userDTO);

        TutorUser tutorUser = tutorUserDAOMapper.mapToDomain(
                tutorUserRepository.findByEmailAddress("example@example.com").orElseThrow()
        );

        tutorUserService.addSpecialization(tutorUser.getId(), List.of(SchoolSubject.MATHEMATICS));

        TutorUserProfileDTO profileDTO = tutorUserService.getTutorUserProfile(tutorUser.getId());
        assertTrue(profileDTO.specializations().contains(SchoolSubject.MATHEMATICS));
    }

    @Test
    void shouldRemoveSpecialization() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.TUTOR
        );

        tutorUserService.registerUser(userDTO);

        TutorUser tutorUser = tutorUserDAOMapper.mapToDomain(
                tutorUserRepository.findByEmailAddress("example@example.com").orElseThrow()
        );

        tutorUserService.addSpecialization(tutorUser.getId(), List.of(SchoolSubject.MATHEMATICS));
        tutorUserService.removeSpecialization(tutorUser.getId(), List.of(SchoolSubject.MATHEMATICS));

        assertTrue(tutorUserRepository.findById(tutorUser.getId()).get()
                .getSpecializations().isEmpty());
    }

    @Test
    void shouldAddReview() {
        UserRegistrationDTO userDTO = new UserRegistrationDTO(
                "testName",
                "testLastName",
                "example@example.com",
                "P@ssword!",
                LocalDate.now().minusYears(17),
                Role.TUTOR
        );

        tutorUserService.registerUser(userDTO);

        String id = tutorUserRepository.findByEmailAddress("example@example.com").get().getId();

        var reviewCreationDTO = new ReviewCreationDTO(
                Rate.FIVE,
                "testComment",
                LocalDateTime.now().minusHours(3),
                new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "testFirstName")
        );

        // student o takim id nie istnieje. trzeba dodac go do studentUserRepository zeby mozna bylo
        // utworzyc new StudentUserReviewLookUpDTO
        tutorUserService.addReview(id, reviewCreationDTO);

        var reviewSet = tutorUserService.getTutorUserById(id).getReviews();

        assertAll(() -> {
            assertFalse(reviewSet.isEmpty());
            assertEquals(reviewCreationDTO.studentUser(),
                    reviewSet.stream().findFirst().get().getStudentUser());
        });
    }

    @Test
    void test() {
        assertEquals(2,2);
    }
}
