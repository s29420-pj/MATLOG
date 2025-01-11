package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTests {

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
    void createReviewNullId() {
        Review review = Review.builder()
                .withComment(testComment)
                .withRate(Rate.FIVE)
                .withDateAndTimeOfReview(testDateTime)
                .withStudent(testStudent)
                .withTutor(testTutor)
                .build();

        assertAll(() -> {
            assertNotNull(review.getId());
            assertEquals(testComment, review.getComment());
            assertEquals(Rate.FIVE, review.getRate());
            assertEquals(testDateTime, review.getDateAndTimeOfComment());
            assertEquals(testStudent, review.getStudentUser());
            assertEquals(testTutor, review.getTutorUser());
        });
    }

    @Test
    void createReviewEmptyId() {
        Review review = Review.builder()
                .withId("")
                .withComment(testComment)
                .withRate(Stars.FIVE)
                .withDateAndTimeOfReview(testDateTime)
                .withStudent(testStudent)
                .withTutor(testTutor)
                .build();

        assertAll(() -> {
            assertNotNull(review.getId());
            assertFalse(review.getId().isEmpty());
            assertEquals(testComment, review.getComment());
            assertEquals(Stars.FIVE, review.getRate());
            assertEquals(testDateTime, review.getDateAndTimeOfComment());
            assertEquals(testStudent, review.getStudentUser());
            assertEquals(testTutor, review.getTutorUser());
        });
    }

    @Test
    void createReviewWithId() {
        Review review = Review.builder()
                .withId(UUID.randomUUID().toString())
                .withComment(testComment)
                .withRate(Stars.FIVE)
                .withDateAndTimeOfReview(testDateTime)
                .withStudent(testStudent)
                .withTutor(testTutor)
                .build();

        assertAll(() -> {
            assertNotNull(review.getId());
            assertEquals(testComment, review.getComment());
            assertEquals(Stars.FIVE, review.getRate());
            assertEquals(testDateTime, review.getDateAndTimeOfComment());
            assertEquals(testStudent, review.getStudentUser());
            assertEquals(testTutor, review.getTutorUser());
        });
    }
}
