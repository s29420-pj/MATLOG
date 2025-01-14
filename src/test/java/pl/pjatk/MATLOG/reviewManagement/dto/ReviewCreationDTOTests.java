package pl.pjatk.MATLOG.reviewManagement.dto;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewCreationDTOTests {


    @Test
    void testReviewCreationDTOInitialization() {
        LocalDateTime testDateTime = LocalDateTime.now().minusHours(2);
        var testStudentUser = new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "testFirstName");
        var reviewCreationDTO = new ReviewCreationDTO(
                Rate.FIVE,
                "testComment",
                testDateTime,
                testStudentUser
        );

        assertAll(() -> {
            assertEquals(Rate.FIVE, reviewCreationDTO.rate());
            assertEquals("testComment", reviewCreationDTO.comment());
            assertEquals(testDateTime, reviewCreationDTO.dateAndTimeOfReview());
            assertEquals(testStudentUser, reviewCreationDTO.studentUser());
        });
    }
}
