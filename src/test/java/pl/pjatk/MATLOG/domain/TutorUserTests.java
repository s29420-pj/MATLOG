package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TutorUserTests {

    // ------------------ happy tests ----------------------

    @Test
    void createTutorUser() {
        TutorUser tutor = new TutorUser.TutorBuilder()
                .withDateOfBirth(LocalDate.now().minusYears(31))
                .build();
        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertNotNull(tutor.getPrivateLessons());
            assertTrue(tutor.getPrivateLessons().isEmpty());
            assertEquals("Emily Rose", tutor.getFullName());
            assertEquals("example@example.com", tutor.getEmailAddress());
            assertEquals(31, tutor.getAge());
        });
    }

    @Test
    void addPrivateLesson() {
        TutorUser tutor = new TutorUser.TutorBuilder()
                .withDateOfBirth(LocalDate.now().minusYears(21))
                .build();

        PrivateLesson privateLesson = new PrivateLesson.PrivateLessonBuilder(tutor.getId(), LocalDate.now().plusMonths(2),
                LocalTime.now(), LocalTime.now().plusMinutes(50))
                .withTitle("testTitle")
                .withDescription("testDescription")
                .withPrice(150.5)
                .build();

        boolean isAdded = tutor.addPrivateLesson(privateLesson);

        assertAll(() -> {
            assertFalse(tutor.getPrivateLessons().isEmpty());
            assertTrue(isAdded);
            assertTrue(tutor.getPrivateLessons().contains(privateLesson));
        });
    }

    @Test
    void createTutorWithProvidedSet() {
        Set<PrivateLesson> set = new HashSet<>();
        PrivateLesson lesson = new PrivateLesson.PrivateLessonBuilder(UUID.randomUUID().toString(), LocalDate.now().plusDays(1),
                LocalTime.now(), LocalTime.now().plusHours(1))
                .build();
        set.add(lesson);

        TutorUser tutor = new TutorUser.TutorBuilder()
                .withPrivateLessons(set)
                        .build();

        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertEquals("Matthew Liam", tutor.getFullName());
            assertEquals("test@example.com", tutor.getEmailAddress());
            assertFalse(tutor.getPrivateLessons().isEmpty());
            assertTrue(tutor.getPrivateLessons().contains(lesson));
        });
    }

    @Test
    void createTutorWithProvidedReviews() {
        Review review = Review.create(Stars.TWO, "comment", UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
        List<Review> reviews = List.of(review);
        TutorUser tutor = new TutorUser.TutorBuilder()
                .withReviews(reviews)
                .build();

        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertEquals("Matthew Liam", tutor.getFullName());
            assertEquals("test@example.com", tutor.getEmailAddress());
            assertFalse(tutor.getReviews().isEmpty());
            assertTrue(tutor.getReviews().contains(review));
        });
    }

}
