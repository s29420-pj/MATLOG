package pl.pjatk.MATLOG.reviewManagement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.pjatk.MATLOG.domain.Review;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;
import pl.pjatk.MATLOG.reviewManagement.exceptions.ReviewNotFoundException;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewRepository;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.userManagement.tutorUser.TutorUserService;
import pl.pjatk.MATLOG.userManagement.user.dto.LoggedUserDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class ReviewServiceIT {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StudentUserRepository studentUserRepository;

    @Autowired
    private TutorUserService tutorUserService;

    @Autowired
    private StudentUserDAOMapper studentUserDAOMapper;

    @Autowired
    private StudentUserReviewDTOMapper studentUserReviewDTOMapper;

    @Autowired
    private UserPasswordValidator userPasswordValidator;

    @Container
    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.datasource.hikari.connection-timeout", () -> 300);
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
    void shouldSaveAndRetrieveReview() {
        StudentUser studentUser = StudentUser.builder()
                .withFirstName("testFirstName")
                .withLastName("testLastName")
                .withEmailAddress("testEmailAddress@example.com")
                .withPassword("P@ssword!", userPasswordValidator)
                .build();
        studentUserRepository.save(studentUserDAOMapper.mapToDAO(studentUser));

        LoggedUserDTO loggedUserDTO = tutorUserService.registerUser(new UserRegistrationDTO(
                "testFirstName",
                "test2",
                "test@example.com",
                "@DFDFfgffsdf2",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        ));


        LocalDateTime dateTime = LocalDateTime.now().minusHours(2);

        ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO(
                Rate.FIVE, loggedUserDTO.getId(), "testComment", dateTime,
                studentUserReviewDTOMapper.mapToStudentReviewLookUpDTO(studentUser));

        ReviewDAO savedReview = reviewService.save(reviewCreationDTO);

        assertThat(savedReview.getStudent()).isNotNull();
        assertThat(savedReview.getDateAndTimeOfReview()).isEqualTo(dateTime.toString());
        assertThat(savedReview.getComment()).isEqualTo("testComment");

        ReviewDTO retrievedReview = reviewService.findReviewDTOById(savedReview.getId());
        assertThat(retrievedReview.comment()).isEqualTo("testComment");
    }

    @Test
    void shouldDeleteReview() {
        StudentUser studentUser = StudentUser.builder()
                .withFirstName("testFirstName")
                .withLastName("testLastName")
                .withEmailAddress("testEmailAddress@example.com")
                .withPassword("P@ssword!", userPasswordValidator)
                .build();
        studentUserRepository.save(studentUserDAOMapper.mapToDAO(studentUser));

        LoggedUserDTO loggedUserDTO = tutorUserService.registerUser(new UserRegistrationDTO(
                "testFirstName",
                "test2",
                "test@example.com",
                "@DFDFfgffsdf2",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        ));

        LocalDateTime dateTime = LocalDateTime.now().minusHours(2);

        ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO(
                Rate.FIVE, loggedUserDTO.getId(), "testComment", dateTime,
                studentUserReviewDTOMapper.mapToStudentReviewLookUpDTO(studentUser));

        ReviewDAO savedReview = reviewService.save(reviewCreationDTO);

        reviewService.remove(savedReview.getId());

        assertThatExceptionOfType(ReviewNotFoundException.class)
                .isThrownBy(() -> reviewService.findReviewDTOById(savedReview.getId()));
    }

    @Test
    void shouldThrowExceptionWhenReviewNotFound() {
        assertThatExceptionOfType(ReviewNotFoundException.class)
                .isThrownBy(() -> reviewService.findReviewDTOById("non-existent-id"));
    }

    @Test
    void shouldMapReviewCreationDTOToDomain() {
        StudentUser studentUser = StudentUser.builder()
                .withFirstName("testFirstName")
                .withLastName("testLastName")
                .withEmailAddress("testEmailAddress@example.com")
                .withPassword("P@ssword!", userPasswordValidator)
                .build();
        studentUserRepository.save(studentUserDAOMapper.mapToDAO(studentUser));

        LocalDateTime dateTime = LocalDateTime.now().minusHours(2);

        LoggedUserDTO loggedUserDTO = tutorUserService.registerUser(new UserRegistrationDTO(
                "testFirstName",
                "test2",
                "test@example.com",
                "@DFDFfgffsdf2",
                LocalDate.now().minusYears(30),
                Role.TUTOR
        ));

        ReviewCreationDTO reviewCreationDTO = new ReviewCreationDTO(
                Rate.FIVE, loggedUserDTO.getId(), "testComment", dateTime,
                studentUserReviewDTOMapper.mapToStudentReviewLookUpDTO(studentUser));

        Review review = reviewService.mapToDomain(reviewCreationDTO);

        assertAll(() -> {
            assertEquals(reviewCreationDTO.rate(), review.getRate());
            assertEquals(reviewCreationDTO.comment(), review.getComment());
            assertEquals(reviewCreationDTO.studentUser(), review.getStudentUser());
            assertEquals(reviewCreationDTO.dateAndTimeOfReview(), review.getDateAndTimeOfComment());
        });
    }

    @Test
    void shouldMapReviewDTOToDomain() {
        StudentUser studentUser = StudentUser.builder()
                .withFirstName("testFirstName")
                .withLastName("testLastName")
                .withEmailAddress("testEmailAddress@example.com")
                .withPassword("P@ssword!", userPasswordValidator)
                .build();
        studentUserRepository.save(studentUserDAOMapper.mapToDAO(studentUser));

        LocalDateTime dateTime = LocalDateTime.now().minusHours(2);

        String reviewDTOId = UUID.randomUUID().toString();
        ReviewDTO reviewDTO = new ReviewDTO(
                reviewDTOId, studentUserReviewDTOMapper.mapToStudentReviewLookUpDTO(studentUser),
                Rate.FOUR, "testComment", dateTime
        );

        Review review = reviewService.mapToDomain(reviewDTO);

        assertAll(() -> {
            assertEquals(reviewDTO.rate(), review.getRate());
            assertEquals(reviewDTO.comment(), review.getComment());
            assertEquals(reviewDTO.studentUserReviewLookUpDTO(), review.getStudentUser());
            assertEquals(reviewDTO.dateAndTimeOfReview(), review.getDateAndTimeOfComment());
        });
    }

    @Test
    void test() {
        assertEquals(2, 2);
    }
}
