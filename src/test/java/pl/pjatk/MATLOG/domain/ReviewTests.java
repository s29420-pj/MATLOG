package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pl.pjatk.MATLOG.domain.enums.Stars;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTests {

    private static final String STUDENT_ID = UUID.randomUUID().toString();
    private static final String TUTOR_ID = UUID.randomUUID().toString();

    // ------------------ happy tests ----------------------
    @ParameterizedTest
    @EnumSource(Stars.class)
    void createReview(Stars stars) {
        Review review = Review.builder()
                        .withRate(stars)
                                .withComment("testComment")
                                        .withStudentId(STUDENT_ID)
                                                .withTutorId(TUTOR_ID)
                                                        .withDateAndTimeOfReview(LocalDateTime.now())
                                                                .build();
        assertAll(() -> {
            assertNotNull(review);
            assertEquals(stars, review.getRate());
            assertEquals("testComment", review.getComment());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
            assertNotNull(review.getDateAndTimeOfComment());
        });
    }

    @Test
    void createReviewWithNullComment() {
        Review review = Review.builder()
                        .withRate(Stars.FOUR)
                                .withComment(null)
                                        .withTutorId(TUTOR_ID)
                                                .withStudentId(STUDENT_ID)
                                                        .build();
        assertAll(() -> {
            assertNotNull(review);
            assertEquals(Stars.FOUR, review.getRate());
            assertEquals("", review.getComment());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
            assertNotNull(review.getDateAndTimeOfComment());
            assertNotNull(review.getDateAndTimeOfComment());
        });
    }

    @Test
    void createReviewWithEmptyComment() {
        Review review = Review.builder()
                        .withRate(Stars.FOUR)
                                .withComment("")
                                        .withStudentId(STUDENT_ID)
                                                .withTutorId(TUTOR_ID)
                                                        .build();
        assertAll(() -> {
            assertNotNull(review);
            assertEquals(Stars.FOUR, review.getRate());
            assertEquals("", review.getComment());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
        });
    }
}
