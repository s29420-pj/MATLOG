package pl.pjatk.MATLOG.PrivateLessonManagment.dto;

import pl.pjatk.MATLOG.UserManagement.tutorUser.dto.PrivateLessonTutorUserDTO;

import java.time.LocalDateTime;

public record PrivateLessonCreateDTO(
        PrivateLessonTutorUserDTO tutor,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price) {}
