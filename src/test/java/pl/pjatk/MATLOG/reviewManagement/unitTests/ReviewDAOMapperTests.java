package pl.pjatk.MATLOG.reviewManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pjatk.MATLOG.Domain.Enums.Rate;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.TutorUserDAO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ReviewDAOMapper.class,
        TutorUserDAOMapper.class,
        StudentUserDAOMapper.class,
        StandardUserPasswordValidator.class})
public class ReviewDAOMapperTests {

    @Autowired
    private ReviewDAOMapper reviewDAOMapper;
    @Autowired
    private TutorUserDAOMapper tutorUserDAOMapper;
    @Autowired
    private StudentUserDAOMapper studentUserDAOMapper;

    private UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

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


    @Test
    void createReviewFromReviewDAO() {
        ReviewDAO reviewDAO = new ReviewDAO(UUID.randomUUID().toString(),
                testComment, Rate.FIVE, testDateTime, testStudentDAO, testTutorDAO);

        Review reviewFromMapper = reviewDAOMapper.create(reviewDAO);

        assertAll(() -> {
            assertNotNull(reviewFromMapper);
            assertEquals(reviewDAO.id(), reviewFromMapper.getId());
            assertEquals(reviewDAO.comment(), reviewFromMapper.getComment());
            assertEquals(reviewDAO.rate(), reviewFromMapper.getRate());
            assertEquals(reviewDAO.dateAndTimeOfComment(), reviewFromMapper.getDateAndTimeOfComment());
            assertEquals(reviewDAO.student().id(), reviewFromMapper.getStudentUser().getId());
            assertEquals(reviewDAO.tutor().id(), reviewFromMapper.getTutorUser().getId());
        });
    }

    @Test
    void createReviewDAOFromReview() {
        Review review = Review.builder()
                .withComment(testComment)
                .withRate(Rate.FOUR)
                .withDateAndTimeOfReview(testDateTime)
                .withStudent(testStudent)
                .withTutor(testTutor)
                .build();

        ReviewDAO reviewDAO = reviewDAOMapper.create(review);

        assertAll(() -> {
            assertEquals(review.getId(), reviewDAO.id());
            assertEquals(review.getComment(), reviewDAO.comment());
            assertEquals(review.getRate(), reviewDAO.rate());
            assertEquals(review.getDateAndTimeOfComment(), reviewDAO.dateAndTimeOfComment());
            assertEquals(review.getStudentUser().getId(), reviewDAO.student().id());
            assertEquals(review.getTutorUser().getId(), reviewDAO.tutor().id());
        });
    }
}
