package pl.pjatk.MATLOG.privateLessonManagement.dto;

import pl.pjatk.MATLOG.userManagement.tutorUser.dto.PrivateLessonTutorUserDTO;

import java.time.LocalDateTime;

public record PrivateLessonCreateDTO(
        String tutorId,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price) {}
