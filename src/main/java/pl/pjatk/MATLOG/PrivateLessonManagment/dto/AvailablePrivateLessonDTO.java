package pl.pjatk.MATLOG.PrivateLessonManagment.dto;

import pl.pjatk.MATLOG.UserManagement.tutorUser.dto.PrivateLessonTutorUserDTO;

import java.time.LocalDateTime;

public record AvailablePrivateLessonDTO(
        String id,
        PrivateLessonTutorUserDTO tutor,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price) {}
