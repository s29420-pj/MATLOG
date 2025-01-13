package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTests {

    @Test
    void testReviewBuilderWithValidData() {

        String reviewId = UUID.randomUUID().toString();
        String comment = "Great lesson!";
        Rate rate = Rate.FIVE;
        LocalDateTime reviewTime = LocalDateTime.now();
        StudentUserReviewLookUpDTO student = new StudentUserReviewLookUpDTO("studentId", "John Doe");

        Review review = Review.builder()
                .withId(reviewId)
                .withComment(comment)
                .withRate(rate)
                .withDateAndTimeOfReview(reviewTime)
                .withStudent(student)
                .build();

        assertEquals(reviewId, review.getId());
        assertEquals(comment, review.getComment());
        assertEquals(rate, review.getRate());
        assertEquals(reviewTime, review.getDateAndTimeOfComment());
        assertEquals(student, review.getStudentUser());
    }

    @Test
    void testReviewBuilderGeneratesRandomIdWhenNotProvided() {
        Rate rate = Rate.FOUR;
        StudentUserReviewLookUpDTO student = new StudentUserReviewLookUpDTO("studentId", "John Doe");

        Review review = Review.builder()
                .withRate(rate)
                .withStudent(student)
                .build();

        assertNotNull(review.getId());
        assertFalse(review.getId().isEmpty());
    }

    @Test
    void testReviewBuilderGeneratesCurrentDateWhenNotProvided() {
        Rate rate = Rate.THREE;
        StudentUserReviewLookUpDTO student = new StudentUserReviewLookUpDTO("studentId", "John Doe");

        Review review = Review.builder()
                .withRate(rate)
                .withStudent(student)
                .build();

        assertNotNull(review.getDateAndTimeOfComment());
        assertTrue(review.getDateAndTimeOfComment().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testReviewBuilderGeneratesEmptyCommentWhenNotProvided() {
        Rate rate = Rate.FIVE;
        StudentUserReviewLookUpDTO student = new StudentUserReviewLookUpDTO("studentId", "John Doe");

        Review review = Review.builder()
                .withRate(rate)
                .withStudent(student)
                .build();

        assertEquals("", review.getComment());
    }

    @Test
    void testReviewBuilderThrowsExceptionForNullRate() {
        StudentUserReviewLookUpDTO student = new StudentUserReviewLookUpDTO("studentId", "John Doe");

        assertThrows(NullPointerException.class, () ->
                Review.builder()
                        .withStudent(student)
                        .build()
        );
    }

    @Test
    void testReviewBuilderThrowsExceptionForNullStudent() {
        Rate rate = Rate.FIVE;

        assertThrows(NullPointerException.class, () ->
                Review.builder()
                        .withRate(rate)
                        .build()
        );
    }

    @Test
    void testReviewBuilderCreatesUniqueIdsForSeparateInstances() {
        StudentUserReviewLookUpDTO student = new StudentUserReviewLookUpDTO("studentId", "John Doe");
        Rate rate = Rate.FIVE;

        Review review1 = Review.builder().withRate(rate).withStudent(student).build();
        Review review2 = Review.builder().withRate(rate).withStudent(student).build();

        assertNotEquals(review1.getId(), review2.getId());
    }
}
