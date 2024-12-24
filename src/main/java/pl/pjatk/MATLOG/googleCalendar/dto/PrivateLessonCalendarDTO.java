package pl.pjatk.MATLOG.googleCalendar.dto;

import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;

import java.time.LocalDateTime;
import java.util.List;

public record PrivateLessonCalendarDTO(
        String id,
        List<SchoolSubject> schoolSubjects,
        String tutorId,
        String studentId,
        String connectionCode,
        PrivateLessonStatus status,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price
) {
}
