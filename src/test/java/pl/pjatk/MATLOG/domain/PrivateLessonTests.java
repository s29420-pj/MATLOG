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
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        // Assert
        assertNotNull(privateLesson);
        assertEquals(tutor, privateLesson.getTutor());
        assertEquals(student, privateLesson.getStudent());
        assertEquals(PrivateLessonStatus.AVAILABLE, privateLesson.getStatus());
        assertEquals(startTime, privateLesson.getStartTime());
        assertEquals(endTime, privateLesson.getEndTime());
        assertEquals(100.0, privateLesson.getPrice());
        assertEquals("Not assigned yet", privateLesson.getConnectionCode());  // Default connection code
    }

    @Test
    void testPrivateLessonCreationWithEmptyConnectionCode() {
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .withConnectionCode("") // Empty connection code
                .build();

        // Assert
        assertEquals("Not assigned yet", privateLesson.getConnectionCode()); // Default connection code when empty
    }

    @Test
    void testPrivateLessonCreationWithValidId() {
        // Arrange
        String lessonId = UUID.randomUUID().toString();
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withId(lessonId)
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        // Assert
        assertEquals(lessonId, privateLesson.getId());
    }

    @Test
    void testPrivateLessonCreationWithGeneratedIdWhenNoIdProvided() {
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        // Assert
        assertNotNull(privateLesson.getId()); // ID should be generated
    }

    @Test
    void testPrivateLessonCreationWithNegativePrice() {
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act & Assert
        assertThrows(PrivateLessonInvalidPriceException.class, () -> {
            PrivateLesson.builder()
                    .withTutor(tutor)
                    .withStudent(student)
                    .withStatus(PrivateLessonStatus.AVAILABLE)
                    .withStartTime(startTime)
                    .withEndTime(endTime)
                    .withPrice(-1.0) // Invalid price
                    .build();
        });
    }

    @Test
    void testPrivateLessonCreationWithNullTutor() {
        // Arrange
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act & Assert
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
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        // Act & Assert
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
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);

        // Act & Assert
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
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        StudentUser student = Mockito.mock(StudentUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStudent(student)
                .withStatus(PrivateLessonStatus.BOOKED)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .withIsAvailableOffline(true) // Offline availability
                .build();

        // Assert
        assertTrue(privateLesson.isAvailableOffline());
    }

    @Test
    void testPrivateLessonWithNullStudent() {
        // Arrange
        TutorUser tutor = Mockito.mock(TutorUser.class);
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        // Act
        PrivateLesson privateLesson = PrivateLesson.builder()
                .withTutor(tutor)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .withPrice(100.0)
                .build();

        // Assert
        assertNull(privateLesson.getStudent());  // Student is optional
    }
}
