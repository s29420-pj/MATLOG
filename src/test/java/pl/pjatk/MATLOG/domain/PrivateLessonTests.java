package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.PrivateLessonInvalidPriceException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PrivateLessonTests {

    @Test
    void testPrivateLessonCreationWithValidData() {

        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withConnectionCode("testConnectionCode")
                .withPrice(100.0)
                .build();

        assertNotNull(privateLesson);
        assertEquals(tutor, privateLesson.getTutor());
        assertEquals(student, privateLesson.getStudent());
        assertEquals(PrivateLessonStatus.AVAILABLE, privateLesson.getStatus());
        assertEquals(startTime, privateLesson.getStartTime());
        assertEquals(endTime, privateLesson.getEndTime());
        assertEquals(100.0, privateLesson.getPrice());
        assertEquals("testConnectionCode", privateLesson.getConnectionCode());
    }

    @Test
    void testPrivateLessonCreationWithEmptyConnectionCode() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .withConnectionCode("") // Empty connection code
                .build();

        assertEquals("Not assigned yet", privateLesson.getConnectionCode());
    }

    @Test
    void testPrivateLessonCreationWithNullConnectionCode() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        assertEquals("Not assigned yet", privateLesson.getConnectionCode());
    }

    @Test
    void testPrivateLessonCreationWithValidId() {
        String lessonId = UUID.randomUUID().toString();
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withId(lessonId)
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        assertEquals(lessonId, privateLesson.getId());
    }

    @Test
    void testPrivateLessonCreationWithGeneratedIdWhenNoIdProvided() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        assertNotNull(privateLesson.getId());
    }

    @Test
    void testPrivateLessonCreationWithNegativePrice() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        assertThrows(PrivateLessonInvalidPriceException.class, () -> {
            PrivateLesson.builder()
                    .withTutor(tutor)
                    .withStudent(student)
                    .withStatus(PrivateLessonStatus.AVAILABLE)
                    .withStartTime(startTime)
                    .withEndTime(endTime)
                    .withPrice(-1.0)
                    .build();
        });
    }

    @Test
    void testPrivateLessonCreationWithNullTutor() {
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        assertThrows(NullPointerException.class, () -> {
            PrivateLesson.builder()
                    .withStudent(student)
                    .withStatus(PrivateLessonStatus.AVAILABLE)
                    .withStartTime(startTime)
                    .withEndTime(endTime)
                    .withPrice(100.0)
                    .build();
        });
    }

    @Test
    void testPrivateLessonCreationWithNullStartTime() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        assertThrows(NullPointerException.class, () -> {
            PrivateLesson.builder()
                    .withTutor(tutor)
                    .withStudent(student)
                    .withStatus(PrivateLessonStatus.AVAILABLE)
                    .withEndTime(endTime)
                    .withPrice(100.0)
                    .build();
        });
    }

    @Test
    void testPrivateLessonCreationWithNullEndTime() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);

        assertThrows(NullPointerException.class, () -> {
            PrivateLesson.builder()
                    .withTutor(tutor)
                    .withStudent(student)
                    .withStatus(PrivateLessonStatus.AVAILABLE)
                    .withStartTime(startTime)
                    .withPrice(100.0)
                    .build();
        });
    }

    @Test
    void testPrivateLessonCreationWithOfflineAvailability() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.BOOKED)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .withIsAvailableOffline(true)
                .build();

        assertTrue(privateLesson.isAvailableOffline());
    }

    @Test
    void testPrivateLessonWithNullStudent() {
        TutorUser tutor = Mockito.mock(TutorUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        assertNull(privateLesson.getStudent());
    }
}
