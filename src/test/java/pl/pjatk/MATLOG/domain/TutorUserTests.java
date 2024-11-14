package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TutorUserTests {

    @Test
    void createTutorUser() {
        TutorUser tutor = new TutorUser.TutorBuilder("Emily", "Rose", "example@example.com")
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
        TutorUser tutor = new TutorUser.TutorBuilder("Matthew", "Liam", "test@example.com")
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

}
