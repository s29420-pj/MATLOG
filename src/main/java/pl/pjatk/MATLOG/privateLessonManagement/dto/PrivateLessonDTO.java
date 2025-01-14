package pl.pjatk.MATLOG.privateLessonManagement.dto;

import java.time.LocalDateTime;

public record PrivateLessonDTO(
        String id,
        String tutorId,
        String studentId,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price) {}
