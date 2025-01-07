package pl.pjatk.MATLOG.PrivateLessonManagment.persistance;

import jakarta.persistence.*;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.tutorUser.persistance.TutorUserDAO;

import java.time.LocalDateTime;

@Entity(name = "private_lesson")
public class PrivateLessonDAO {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private TutorUserDAO tutor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentUserDAO student;

    private String connectionCode;

    private PrivateLessonStatus status;

    private boolean isAvailableOffline;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double price;
}
