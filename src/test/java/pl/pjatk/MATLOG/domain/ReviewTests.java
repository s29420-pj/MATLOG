package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.pjatk.MATLOG.domain.exceptions.reviewExceptions.ReviewInvalidRateException;
import pl.pjatk.MATLOG.domain.exceptions.reviewExceptions.ReviewInvalidStudentId;
import pl.pjatk.MATLOG.domain.exceptions.reviewExceptions.ReviewInvalidTutorId;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTests {

    private static final String STUDENT_ID = UUID.randomUUID().toString();
    private static final String TUTOR_ID = UUID.randomUUID().toString();

    // ------------------ happy tests ----------------------
    @ParameterizedTest
    @EnumSource(Stars.class)
    void createReview(Stars stars) {
        Review review = Review.create(stars, "testComment", STUDENT_ID, TUTOR_ID);
        assertAll(() -> {
            assertNotNull(review);
            assertEquals(stars, review.getRate());
            assertEquals("testComment", review.getComment());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
        });
    }

    @Test
    void createReviewFrom() {
        Review review = Review.create(Stars.FIVE, "testComment",
                STUDENT_ID, TUTOR_ID);
        Review reviewToAssert = Review.from(review);
        assertAll(() -> {
            assertNotEquals(review.getId(), reviewToAssert.getId());
            assertEquals(review.getRate(), reviewToAssert.getRate());
            assertEquals(review.getComment(), reviewToAssert.getComment());
            assertEquals(review.getStudentId(), reviewToAssert.getStudentId());
            assertEquals(review.getTutorId(), reviewToAssert.getTutorId());
        });
    }

    @Test
    void createReviewWithNullComment() {
        Review review = Review.create(Stars.FOUR, null, STUDENT_ID,
                TUTOR_ID);
        assertAll(() -> {
            assertNotNull(review);
            assertEquals(Stars.FOUR, review.getRate());
            assertEquals("", review.getComment());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
        });
    }

    @Test
    void createReviewWithEmptyComment() {
        Review review = Review.create(Stars.FOUR, "", STUDENT_ID,
                TUTOR_ID);
        assertAll(() -> {
            assertNotNull(review);
            assertEquals(Stars.FOUR, review.getRate());
            assertEquals("", review.getComment());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
        });
    }

    @Test
    void createReviewWithoutComment() {
        Review review = Review.create(Stars.TWO, STUDENT_ID, TUTOR_ID);
        assertAll(() -> {
            assertNotNull(review.getId());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
            assertEquals(Stars.TWO, review.getRate());
            assertEquals("", review.getComment());
        });
    }

    // ------------------ exceptions tests ----------------------

    @Test
    void nullRateException() {
        assertThrows(ReviewInvalidRateException.class, () -> {
            Review.create(null, "testComment", UUID.randomUUID().toString(),
                    UUID.randomUUID().toString());
        });
    }

    @Test
    void nullStudentIdException() {
        assertThrows(ReviewInvalidStudentId.class, () -> {
            Review.create(Stars.TWO, "testComment", null, UUID.randomUUID().toString());
        });
    }

    @Test
    void emptyStudentIdException() {
        assertThrows(ReviewInvalidStudentId.class, () -> {
            Review.create(Stars.TWO, "testComment", "", UUID.randomUUID().toString());
        });
    }

    @Test
    void nullTutorIdException() {
        assertThrows(ReviewInvalidTutorId.class, () -> {
            Review.create(Stars.ONE, "TestComment", UUID.randomUUID().toString(), null);
        });
    }

    @Test
    void emptyTutorIdException() {
        assertThrows(ReviewInvalidTutorId.class, () -> {
            Review.create(Stars.TWO, "testComment", UUID.randomUUID().toString(), "");
        });
    }

}
