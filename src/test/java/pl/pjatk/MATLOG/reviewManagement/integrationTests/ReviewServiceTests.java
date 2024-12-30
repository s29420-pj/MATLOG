package pl.pjatk.MATLOG.reviewManagement.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.reviewManagement.ReviewRepository;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class ReviewServiceTests {

    private UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

    @Container
    @ServiceConnection
    static MongoDBContainer mongo = new MongoDBContainer("mongo:latest");

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private TutorUserDAOMapper tutorUserDAOMapper;
    @Autowired
    private TutorUserDTOMapper tutorUserDTOMapper;
    @Autowired
    private StudentUserDAOMapper studentUserDAOMapper;
    @Autowired
    private StudentUserReviewDTOMapper studentUserReviewDTOMapper;
    @Autowired
    private ReviewDAOMapper reviewDAOMapper;
    @Autowired
    private ReviewDTOMapper reviewDTOMapper;
    @Autowired
    private ReviewService reviewService;

    private final String testComment = "testComment";
    private final LocalDateTime testDateTime = LocalDateTime.now().minusHours(3);
    private final StudentUser testStudent = StudentUser.builder()
            .withFirstName("Ethan")
            .withLastName("Hovermann")
            .withEmailAddress("example@example.com")
            .withPassword("testPassword!", userPasswordValidator)
            .withDateOfBirth(LocalDate.now().minusYears(50))
            .withIsAccountNonLocked(true)
            .build();
    private final StudentUserDAO testStudentDAO = new StudentUserDAO(
            testStudent.getId(),
            testStudent.getFirstName(),
            testStudent.getLastName(),
            testStudent.getEmailAddress(),
            testStudent.getPassword(),
            testStudent.getDateOfBirth(),
            testStudent.getAuthorities(),
            testStudent.isAccountNonLocked());
    private final TutorUser testTutor = TutorUser.builder()
            .withFirstName("Emily")
            .withLastName("Rose")
            .withEmailAddress("example@example.com")
            .withPassword("testP@ssword", userPasswordValidator)
            .withBiography("Im happy")
            .withIsAccountNonLocked(true)
            .withDateOfBirth(LocalDate.now().minusYears(31))
            .build();;
    private final TutorUserDAO testTutorDAO = new TutorUserDAO(testTutor.getId(),
            testTutor.getFirstName(),
            testTutor.getLastName(),
            testTutor.getEmailAddress(),
            testTutor.getPassword(),
            testTutor.getDateOfBirth(),
            testTutor.getBiography(),
            testTutor.getSpecializations(),
            testTutor.getAuthorities(),
            testTutor.isAccountNonLocked());

    @BeforeEach
    void setUpData() {
        reviewRepository.save(new ReviewDAO("testId",
                testComment,
                Stars.ONE,
                testDateTime,
                testStudentDAO,
                testTutorDAO));
    }

    @Test
    void getTutorReviewsDTOByEmailAddress() {
        List<ReviewLookUpDTO> tutorReviewsDTOByEmailAddress = reviewService
                .getTutorReviewsDTOByEmailAddress("example@example.com");

        assertAll(() -> {
            assertEquals(1, tutorReviewsDTOByEmailAddress.size());
            assertEquals(testComment, tutorReviewsDTOByEmailAddress.getFirst().comment());
        });
    }


}
