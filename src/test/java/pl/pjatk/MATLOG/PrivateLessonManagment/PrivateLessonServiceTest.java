package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.Domain.PrivateLesson;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

class PrivateLessonServiceTest {

    private PrivateLessonRepository privateLessonRepository;
    private PrivateLessonService privateLessonService;

    @BeforeEach
    void setUp() {
        privateLessonRepository = mock(PrivateLessonRepository.class);
        privateLessonService = new PrivateLessonService(privateLessonRepository);
    }

    @Test
    void shouldCreateNewLesson() {
        PrivateLesson existingPrivateLesson = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC))
                .withTutorId(UUID.randomUUID().toString())
                .withStartTime(LocalDateTime.now().plusDays(1).plusHours(12))
                .withEndTime(LocalDateTime.now().plusDays(1).plusHours(13))
                .withPrice(80.0)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .build();

        privateLessonService.createPrivateLesson(existingPrivateLesson);
    }
}