package pl.pjatk.MATLOG.PrivateLessonManagment.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAO;

import java.time.LocalDateTime;

@Entity(name = "private_lesson")
@Getter
public class PrivateLessonDAO {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private TutorUserDAO tutor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentUserDAO student;

    @Column(name = "connection_code", nullable = true, unique = true)
    private String connectionCode;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PrivateLessonStatus status;

    @Column(name = "is_available_offline", nullable = false)
    private boolean isAvailableOffline;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "price", nullable = false)
    private Double price;

    public PrivateLessonDAO() {
    }

    public PrivateLessonDAO(String id,
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
