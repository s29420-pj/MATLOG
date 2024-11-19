package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidDateException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidOwnerIdException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidPriceException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateLessonTests {

    // ------------------ happy tests ----------------------

    @Test
    void createPrivateLesson() {
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withOwnerId(UUID.randomUUID().toString())
                .withStartTime(LocalDateTime.now().plusDays(1))
                .withEndTime(LocalDateTime.now().plusDays(1).plusHours(2))
                .withTitle("testTitle")
                .withDescription("testDescription")
                .withPrice(85.0)
                .build();
        assertAll(() -> {
            assertNotNull(privateLesson.getOwnerId());
            assertEquals("testTitle", privateLesson.getTitle());
            assertEquals("testDescription", privateLesson.getDescription());
            assertNotNull(privateLesson.getStartTime());
            assertNotNull(privateLesson.getEndTime());
            assertEquals(85.0, privateLesson.getPrice());
        });
    }

    // ------------------ exceptions tests ----------------------

    @Test
    void nullOwnerIdException() {
        assertThrows(LessonInvalidOwnerIdException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId(null)
                    .build();
        });
    }

    @Test
    void emptyOwnerIdException() {
        assertThrows(LessonInvalidOwnerIdException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId("")
                    .build();
        });
    }

    @Test
    void notSetOwnerIdInBuilder() {
        assertThrows(LessonInvalidOwnerIdException.class, () -> {
            PrivateLesson.builder()
                    .withStartTime(LocalDateTime.now().plusHours(1))
                    .withEndTime(LocalDateTime.now().plusHours(2))
                    .build();
        });
    }

    @Test
    void priceBelow0Test() {
        assertThrows(LessonInvalidPriceException.class, () -> {
            PrivateLesson.builder()
                    .withPrice(-1.1)
                    .build();
        });
    }

    @Test
    void notSetStartTimeInBuilder() {
        assertThrows(IllegalStateException.class,() -> {
            PrivateLesson.builder()
                    .withOwnerId(UUID.randomUUID().toString())
                    .withEndTime(LocalDateTime.now().plusHours(3))
                    .build();
        });
    }
}
