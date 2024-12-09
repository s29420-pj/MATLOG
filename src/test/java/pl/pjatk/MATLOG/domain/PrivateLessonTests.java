package pl.pjatk.MATLOG.Domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;
import pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateLessonTests {

    // ------------------ happy tests ----------------------

    @Test
    void createPrivateLesson() {
        List<SchoolSubject> testList = Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC);

        PrivateLesson testPrivateLesson = PrivateLesson.builder()
                .withSchoolSubjects(testList)
                .withTutorId(UUID.randomUUID().toString())
                .withStartTime(LocalDateTime.now().plusDays(1))
                .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .withPrice(80.0)
                .build();

        assertNotNull(testPrivateLesson);
    }

    // ------------------ exceptions tests ----------------------

    @Test
    void nullSchoolSubjects() {
        assertThrows(PrivateLessonInvalidSchoolSubjectException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(null)
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void nullTutorId() {
        assertThrows(PrivateLessonInvalidIdException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(null)
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void emptyTutorId() {
        assertThrows(PrivateLessonInvalidIdException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId("")
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void studentIdNotAssigned() {
        PrivateLesson testPrivateLesson = PrivateLesson.builder()
                .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                .withTutorId(UUID.randomUUID().toString())
                .withStartTime(LocalDateTime.now().plusDays(1))
                .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .withPrice(80.0)
                .build();

        assertEquals("not assigned yet", testPrivateLesson.getStudentId());
    }

    @Test
    void connectionCodeNotAssigned() {
        PrivateLesson testPrivateLesson = PrivateLesson.builder()
                .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                .withTutorId(UUID.randomUUID().toString())
                .withStartTime(LocalDateTime.now().plusDays(1))
                .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .withPrice(80.0)
                .build();

        assertEquals("not assigned yet", testPrivateLesson.getConnectionCode());
    }

    @Test
    void startTimeNotSet() {
        assertThrows(IllegalStateException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void endTimeNotSet() {
        assertThrows(IllegalStateException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void startTimeIsBeforeNow() {
        assertThrows(PrivateLessonInvalidStartTimeException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(LocalDateTime.now().minusHours(1))
                    .withEndTime(LocalDateTime.now())
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void startTimeIsAfterEndTime() {
        assertThrows(PrivateLessonInvalidEndTimeException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withEndTime(LocalDateTime.now().plusDays(1).minusHours(1))
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void endTimeIsBeforeNow() {
        assertThrows(PrivateLessonInvalidEndTimeException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withEndTime(LocalDateTime.now().minusHours(1))
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void endTimeIsEqualToStartTime() {
        LocalDateTime sameDateTime = LocalDateTime.now().plusHours(1);
        assertThrows(PrivateLessonInvalidEndTimeException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(sameDateTime)
                    .withEndTime(sameDateTime)
                    .withPrice(80.0)
                    .build();
        });
    }

    @Test
    void priceIsNull() {
        assertThrows(PrivateLessonInvalidPriceException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                    .withPrice(null)
                    .build();
        });
    }

    @Test
    void priceBelowZero() {
        assertThrows(PrivateLessonInvalidPriceException.class, () -> {
            PrivateLesson.builder()
                    .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                    .withTutorId(UUID.randomUUID().toString())
                    .withStartTime(LocalDateTime.now().plusDays(1))
                    .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                    .withPrice(-1.1)
                    .build();
        });
    }

    @Test
    void priceEqualToZero() {
        PrivateLesson testPrivateLesson = PrivateLesson.builder()
                .withSchoolSubjects(Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                .withTutorId(UUID.randomUUID().toString())
                .withStartTime(LocalDateTime.now().plusDays(1))
                .withEndTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .withPrice(0.0)
                .build();

        assertEquals(0.0, testPrivateLesson.getPrice());
    }

}