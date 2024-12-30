package pl.pjatk.MATLOG.reviewManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDTOMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ReviewDTOMapper.class,
        StudentUserReviewDTOMapper.class,
        StandardUserPasswordValidator.class})
public class ReviewLookUpDTOMapperTests {

    @Autowired
    private ReviewDTOMapper reviewDTOMapper;

    @Autowired
    private UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

    private final StudentUser testStudent = StudentUser.builder()
            .withFirstName("Ethan")
            .withLastName("Hovermann")
            .withEmailAddress("example@example.com")
            .withPassword("testPassword!", userPasswordValidator)
            .withDateOfBirth(LocalDate.now().minusYears(50))
            .withIsAccountNonLocked(true)
            .build();

    private final TutorUser testTutor = TutorUser.builder()
            .withFirstName("Emily")
            .withLastName("Rose")
            .withEmailAddress("example@example.com")
            .withPassword("testP@ssword", userPasswordValidator)
            .withBiography("Im happy")
            .withIsAccountNonLocked(true)
            .withDateOfBirth(LocalDate.now().minusYears(31))
            .build();

    @Test
    void createReviewDTOFromReview() {
        Review review = Review.builder()
                .withComment("testComment")
                .withRate(Stars.FIVE)
                .withDateAndTimeOfReview(LocalDateTime.now().minusDays(1))
                .withStudent(testStudent)
                .withTutor(testTutor)
                .build();

        ReviewLookUpDTO reviewLookUpDTO = reviewDTOMapper.create(review);

        assertAll(() -> {
            assertEquals(review.getComment(), reviewLookUpDTO.comment());
            assertEquals(review.getRate(), reviewLookUpDTO.rate());
            assertEquals(review.getDateAndTimeOfComment(), reviewLookUpDTO.dateAndTimeOfReview());
            assertEquals(review.getStudentUser().getFirstName(), reviewLookUpDTO.studentUser().firstName());
            assertEquals(review.getStudentUser().getLastName(), reviewLookUpDTO.studentUser().lastName());
        });
    }
}
