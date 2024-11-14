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

    // ------------------ happy tests ----------------------
    @ParameterizedTest
    @EnumSource(Stars.class)
    void createReview(Stars stars) {
        Review review = Review.create(stars, "testComment",
                UUID.randomUUID().toString(), UUID.randomUUID().toString());
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
                UUID.randomUUID().toString(), UUID.randomUUID().toString());
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
        Review review = Review.create(Stars.FOUR, null, UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
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
        Review review = Review.create(Stars.FOUR, "", UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
        assertAll(() -> {
            assertNotNull(review);
            assertEquals(Stars.FOUR, review.getRate());
            assertEquals("", review.getComment());
            assertNotNull(review.getStudentId());
            assertNotNull(review.getTutorId());
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
