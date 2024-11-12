package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateLessonTests {

    @Test
    void createPrivateLesson() {
        Lesson privateLesson = new PrivateLesson.PrivateLessonBuilder()
                .withOwnerId(UUID.randomUUID().toString())
                .withTitle("testTitle")
                .withDescription("testDescription")
                .withDate(LocalDate.now().plusDays(3))
                .withStartTime(LocalTime.now())
                .withEndTime(LocalTime.now().plusMinutes(45L))
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
}
