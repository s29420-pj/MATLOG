package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateLessonTests {

    // ------------------ happy tests ----------------------

    @Test
    void createPrivateLesson() {
        Lesson privateLesson = new PrivateLesson.PrivateLessonBuilder(UUID.randomUUID().toString(), LocalDate.now().plusDays(3),
                LocalTime.now(), LocalTime.now().plusMinutes(45))
                .withTitle("testTitle")
                .withDescription("testDescription")
                .withPrice(85.0)
                .build();
        assertAll(() -> {
            assertNotNull(privateLesson.getOwnerId());
            assertEquals("testTitle", privateLesson.getTitle());
            assertEquals("testDescription", privateLesson.getDescription());
            assertEquals(LocalDate.now().plusDays(3), privateLesson.getDate());
            assertNotNull(privateLesson.getStartTime());
            assertNotNull(privateLesson.getEndTime());
            assertEquals(85.0, privateLesson.getPrice());
        });
    }

    // ------------------ exceptions tests ----------------------

    @Test
    void nullDatePrivateLesson() {
        Lesson privateLesson = new PrivateLesson.PrivateLessonBuilder()
    }
}
