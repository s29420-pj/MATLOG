package pl.pjatk.MATLOG.PrivateLessonManagment.persistance;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserDAO;

import java.time.LocalDateTime;

@Document("booked_private_lesson")
public record BookedPrivateLessonDAO(
        @MongoId String id,
        @DBRef TutorUserDAO tutor,
        boolean isAvailableOffline,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price,
        @DBRef StudentUserDAO student,
        String connectionCode,
        boolean isPaid) {}
