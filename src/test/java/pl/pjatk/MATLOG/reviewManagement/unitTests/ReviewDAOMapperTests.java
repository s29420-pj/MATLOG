package pl.pjatk.MATLOG.reviewManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserDAO;
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
    private TutorUserDAOMapper tutorUserDAOMapper;
    @Autowired
    private StudentUserDAOMapper studentUserDAOMapper;
    @Autowired
    private UserPasswordValidator passwordValidator;
    @Autowired
    private ReviewDAOMapper reviewDAOMapper;


    private static final String testComment = "testComment";
    private static final LocalDateTime testDateTime = LocalDateTime.now().minusHours(3);
    private static final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();
    private static final StudentUser testStudent = StudentUser.builder()
            .withFirstName("Ethan")
            .withLastName("Hovermann")
            .withEmailAddress("example@example.com")
            .withPassword("testPassword!", userPasswordValidator)
            .withDateOfBirth(LocalDate.now().minusYears(50))
            .withIsAccountNonLocked(true)
            .build();
    private static final TutorUser testTutor = TutorUser.builder()
            .withFirstName("Emily")
            .withLastName("Rose")
            .withEmailAddress("example@example.com")
            .withPassword("testP@ssword", userPasswordValidator)
            .withBiography("Im happy")
            .withIsAccountNonLocked(true)
            .withDateOfBirth(LocalDate.now().minusYears(31))
            .build();

    @Test
    void createReviewFromReviewDAO() {
        StudentUserDAO testStudentDAO = new StudentUserDAO(testStudent.getId(),
                testStudent.getFirstName(),
                testStudent.getLastName(),
                testStudent.getEmailAddress(),
                testStudent.getPassword(),
                testStudent.getDateOfBirth(),
                testStudent.getAuthorities(),
                testStudent.isAccountNonLocked());

        TutorUserDAO testTutorDAO = new TutorUserDAO(testTutor.getId(),
                testTutor.getFirstName(),
                testTutor.getLastName(),
                testTutor.getEmailAddress(),
                testTutor.getPassword(),
                testTutor.getDateOfBirth(),
                testTutor.getBiography(),
                testTutor.getSpecializations(),
                testTutor.getAuthorities(),
                testTutor.isAccountNonLocked());

        ReviewDAO reviewDAO = new ReviewDAO(UUID.randomUUID().toString(),
                testComment, Stars.FIVE, testDateTime, testStudentDAO, testTutorDAO);

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
}
