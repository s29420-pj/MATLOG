package pl.pjatk.MATLOG.reviewManagement.dto;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewDTOTests {

    @Test
    void createReviewDTO() {
        String id = UUID.randomUUID().toString();
        var student = new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "testFirstName");
        var dateTime = LocalDateTime.now().minusHours(2);
        var reviewDTO = new ReviewDTO(id, student, Rate.ONE, "poor", dateTime);

        assertAll(() -> {
            assertEquals(id, reviewDTO.id());
            assertEquals(student.firstName(), reviewDTO.studentUserReviewLookUpDTO().firstName());
            assertEquals(Rate.ONE, reviewDTO.rate());
            assertEquals("poor", reviewDTO.comment());
            assertEquals(dateTime, reviewDTO.dateAndTimeOfReview());
        });
    }
}
