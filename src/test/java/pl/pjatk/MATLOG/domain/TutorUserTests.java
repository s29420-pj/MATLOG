package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TutorUserTests {

    @Test
    void createTutorUser() {
        TutorUser tutor = new TutorUser.TutorBuilder()
                .withFirstName("Emily")
                .withLastName("Rose")
                .withEmailAddress("example@example.com")
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
                .withFirstName("Matthew")
                .withLastName("Liam")
                .withEmailAddress("example@example.com")
                .withDateOfBirth(LocalDate.now().minusYears(21))
                .build();

        PrivateLesson privateLesson = new PrivateLesson.PrivateLessonBuilder()
                .withOwnerId(tutor.getId())
                .withTitle("testTitle")
                .withDescription("testDescription")
                .withDate(LocalDate.now().plusMonths(2))
                .withStartTime(LocalTime.now())
                .withEndTime(LocalTime.now().plusMinutes(50))
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
