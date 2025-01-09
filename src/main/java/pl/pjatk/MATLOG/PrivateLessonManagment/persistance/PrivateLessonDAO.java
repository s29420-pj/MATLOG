package pl.pjatk.MATLOG.PrivateLessonManagment.persistance;

import jakarta.persistence.*;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAO;

import java.time.LocalDateTime;

@Entity(name = "private_lesson")
public class PrivateLessonDAO {

    @Id
    String id;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    TutorUserDAO tutor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    StudentUserDAO student;

    @Column(nullable = true, unique = true)
    String connectionCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PrivateLessonStatus status;

    @Column(nullable = false)
    boolean isAvailableOffline;

    @Column(nullable = false)
    LocalDateTime startTime;

    @Column(nullable = false)
    LocalDateTime endTime;

    @Column(nullable = false)
    Double price;

    protected PrivateLessonDAO() {
    }

    PrivateLessonDAO(String id,
                            TutorUserDAO tutor,
                            StudentUserDAO student,
                            String connectionCode,
                            PrivateLessonStatus status,
                            boolean isAvailableOffline,
                            LocalDateTime startTime,
                            LocalDateTime endTime,
                            Double price) {
        this.id = id;
        this.tutor = tutor;
        this.student = student;
        this.connectionCode = connectionCode;
        this.status = status;
        this.isAvailableOffline = isAvailableOffline;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }
}
