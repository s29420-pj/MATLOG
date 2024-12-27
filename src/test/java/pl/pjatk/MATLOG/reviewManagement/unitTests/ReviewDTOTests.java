package pl.pjatk.MATLOG.reviewManagement.unitTests;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.student.dto.StudentUserReviewDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewDTOTests {

    private static final String testComment = "testComment";
    private static final LocalDateTime testDateTime = LocalDateTime.now().minusHours(3);
    private static final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();
    private static final StudentUserReviewDTO testStudent = new StudentUserReviewDTO("Mike", "Mayer");

    @Test
    void createReviewDTO() {
        ReviewDTO reviewDTO = new ReviewDTO(testComment, Stars.FIVE, testDateTime, testStudent);

        assertAll(() -> {
            assertEquals(testComment, reviewDTO.comment());
            assertEquals(Stars.FIVE, reviewDTO.rate());
            assertEquals(testDateTime, reviewDTO.dateAndTimeOfReview());
            assertEquals(testStudent, reviewDTO.studentUser());
        });
    }
}
