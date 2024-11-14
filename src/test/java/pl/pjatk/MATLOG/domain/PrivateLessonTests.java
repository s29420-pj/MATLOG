package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidDateException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidOwnerIdException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidTimeException;

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
    void nullOwnerIdException() {
        assertThrows(LessonInvalidOwnerIdException.class, () -> {
            new PrivateLesson.PrivateLessonBuilder(null, LocalDate.now().plusDays(1),
                    LocalTime.now(), LocalTime.now().plusHours(1)).build();
        });
    }

    @Test
    void emptyOwnerIdException() {
        assertThrows(LessonInvalidOwnerIdException.class, () -> {
            new PrivateLesson.PrivateLessonBuilder("", LocalDate.now().plusDays(1),
                    LocalTime.now(), LocalTime.now().plusHours(1)).build();
        });
    }

    @Test
    void nullDateException() {
        assertThrows(LessonInvalidDateException.class, () -> {
            new PrivateLesson.PrivateLessonBuilder(UUID.randomUUID().toString(),
                    null, LocalTime.now(), LocalTime.now().plusHours(1)).build();
        });
    }

    @Test
    void dateIsBeforeNowException() {
        assertThrows(LessonInvalidDateException.class, () -> {
            new PrivateLesson.PrivateLessonBuilder(UUID.randomUUID().toString(), LocalDate.now().minusDays(1),
                    LocalTime.now(), LocalTime.now().plusHours(1)).build();
        });
    }

    @Test
    void dateIsNowAndStartTimeIsBeforeNowException() {
        assertThrows(LessonInvalidTimeException.class, () -> {
            new PrivateLesson.PrivateLessonBuilder(UUID.randomUUID().toString(), LocalDate.now(),
                    LocalTime.now().minusHours(1), LocalTime.now()).build();
        });
    }

    @Test
    void startTimeIsAfterEndTimeException() {
        assertThrows(LessonInvalidTimeException.class, () -> {
            new PrivateLesson.PrivateLessonBuilder(UUID.randomUUID().toString(), LocalDate.now(),
                    LocalTime.now(), LocalTime.now().minusHours(1)).build();
        });
    }
}
