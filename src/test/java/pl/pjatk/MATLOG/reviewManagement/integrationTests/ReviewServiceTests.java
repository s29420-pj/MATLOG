package pl.pjatk.MATLOG.reviewManagement.integrationTests;

import com.tngtech.archunit.lang.extension.EvaluatedRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.pjatk.MATLOG.Domain.Enums.Rate;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.studentUser.StudentUserService;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.TutorUserService;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.TutorUserDAO;
import pl.pjatk.MATLOG.reviewManagement.ReviewRepository;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewCreationDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewLookUpDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {ReviewDAOMapper.class, ReviewLookUpDTOMapper.class, TutorUserDAOMapper.class, StudentUserDAOMapper.class, StandardUserPasswordValidator.class,
StudentUserReviewDTOMapper.class, ReviewService.class, ReviewCreationDTOMapper.class})
@Testcontainers
public class ReviewServiceTests {

    private UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();
    @MockBean
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewCreationDTOMapper reviewCreationDTOMapper;
    @Autowired
    private ReviewDAOMapper reviewDAOMapper;
    @Autowired
    private ReviewLookUpDTOMapper reviewLookUpDTOMapper;
    @MockBean
    private StudentUserService studentUserService;
    @MockBean
    private TutorUserService tutorUserService;
    @InjectMocks
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
    private final ReviewDAO testReviewDAO = new ReviewDAO("123", testComment,
            Rate.FOUR, testDateTime, testStudentDAO, testTutorDAO);
    private final ReviewDAO testReviewDAO2 = new ReviewDAO("1234", testComment,
            Rate.ONE, testDateTime, testStudentDAO, testTutorDAO);

    @Test
    void getTutorReviewsDTOByEmailAddress() {

        when(reviewRepository.findAllByTutor_EmailAddress("example@example.com"))
                .thenReturn(List.of(testReviewDAO));

        List<ReviewLookUpDTO> tutorReviewsDTOByEmailAddress = reviewService
                .getTutorReviewsDTOByEmailAddress("example@example.com");

        assertAll(() -> {
            assertEquals(1, tutorReviewsDTOByEmailAddress.size());
            assertEquals(testComment, tutorReviewsDTOByEmailAddress.getFirst().comment());
        });
    }

    @Test
    void getStudentReviewsDTOByEmailAddress() {

        when(reviewRepository.findAllByStudent_EmailAddress("example@example.com"))
                .thenReturn(List.of(testReviewDAO, testReviewDAO2));

        List<ReviewLookUpDTO> studentReviewsDTOByEmailAddress = reviewService
                .getStudentReviewsDTOByEmailAddress("example@example.com");

        assertAll(() -> {
            assertEquals(2, studentReviewsDTOByEmailAddress.size());
            assertEquals(Rate.FOUR, studentReviewsDTOByEmailAddress.getFirst().rate());
            assertEquals(Rate.ONE, studentReviewsDTOByEmailAddress.getLast().rate());
        });
    }


}
