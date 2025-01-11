package pl.pjatk.MATLOG.privateLessonManagement.dto;

import pl.pjatk.MATLOG.userManagement.studentUser.dto.PrivateLessonStudentUserDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.PrivateLessonTutorUserDTO;

import java.time.LocalDateTime;

public record PrivateLessonDTO(
        String id,
        PrivateLessonTutorUserDTO tutor,
        PrivateLessonStudentUserDTO student,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price) {}
