package pl.pjatk.MATLOG.reviewManagement.unitTests;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.Domain.Enums.Rate;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.studentUser.dto.StudentUserReviewLookUpDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewLookUpDTOTests {

    private static final String testComment = "testComment";
    private static final LocalDateTime testDateTime = LocalDateTime.now().minusHours(3);
    private static final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();
    private static final StudentUserReviewLookUpDTO testStudent = new StudentUserReviewLookUpDTO("Mike", "Mayer");

    @Test
    void createReviewDTO() {
        ReviewLookUpDTO reviewLookUpDTO = new ReviewLookUpDTO(testComment, Rate.FIVE, testDateTime, testStudent);

        assertAll(() -> {
            assertEquals(testComment, reviewLookUpDTO.comment());
            assertEquals(Rate.FIVE, reviewLookUpDTO.rate());
            assertEquals(testDateTime, reviewLookUpDTO.dateAndTimeOfReview());
            assertEquals(testStudent, reviewLookUpDTO.studentUser());
        });
    }
}
