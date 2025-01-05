package pl.pjatk.MATLOG.PrivateLessonManagment.dto;

import org.springframework.cglib.core.Local;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.PrivateLessonTutorUserDTO;

import java.time.LocalDateTime;

public record AvailablePrivateLessonDTO(
        String id,
        PrivateLessonTutorUserDTO tutor,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price) {}
