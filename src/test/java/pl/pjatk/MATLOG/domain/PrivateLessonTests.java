package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateLessonTests {

    private static final String OWNER_ID = UUID.randomUUID().toString();

    // ------------------ happy tests ----------------------

    @Test
    void createPrivateLesson() {
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withOwnerId(OWNER_ID)
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
        assertThrows(IllegalStateException.class, () -> {
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
                    .withOwnerId(OWNER_ID)
                    .withEndTime(LocalDateTime.now().plusHours(3))
                    .build();
        });
    }

    @Test
    void notSetEndTimeInBuilder() {
        assertThrows(IllegalStateException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId(OWNER_ID)
                    .withStartTime(LocalDateTime.now().plusHours(3))
                    .build();
        });
    }

    @Test
    void startTimeIsBeforeNow() {
        assertThrows(PrivateLessonInvalidStartTimeException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId(OWNER_ID)
                    .withStartTime(LocalDateTime.now().minusHours(1))
                    .withEndTime(LocalDateTime.now())
                    .build();
        });
    }

    @Test
    void startTimeIsBeforeEndTime() {
        assertThrows(PrivateLessonInvalidEndTimeException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId(OWNER_ID)
                    .withStartTime(LocalDateTime.now().plusHours(3))
                    .withEndTime(LocalDateTime.now().plusHours(1))
                    .build();
        });
    }

    @Test
    void endTimeIsBeforeNow() {
        assertThrows(PrivateLessonInvalidEndTimeException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId(OWNER_ID)
                    .withStartTime(LocalDateTime.now().plusHours(1))
                    .withEndTime(LocalDateTime.now().minusHours(1))
                    .build();
        });
    }

    @Test
    void endTimeIsBeforeStartTime() {
        assertThrows(PrivateLessonInvalidEndTimeException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId(OWNER_ID)
                    .withStartTime(LocalDateTime.now().plusHours(3))
                    .withEndTime(LocalDateTime.now().plusHours(1))
                    .build();
        });
    }

    @Test
    void endTimeIsEqualToStartTime() {
        LocalDateTime sameDateTime = LocalDateTime.now().plusHours(1);
        assertThrows(PrivateLessonInvalidEndTimeException.class, () -> {
            PrivateLesson.builder()
                    .withOwnerId(OWNER_ID)
                    .withStartTime(sameDateTime)
                    .withEndTime(sameDateTime)
                    .build();
        });
    }
}
