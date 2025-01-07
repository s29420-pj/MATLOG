package pl.pjatk.MATLOG.reviewManagement.persistance;

import jakarta.persistence.*;
import pl.pjatk.MATLOG.Domain.Enums.Rate;
import pl.pjatk.MATLOG.UserManagement.studentUser.persistance.StudentUserDAO;

import java.time.LocalDateTime;

/**
 *
 */
@Entity
public class ReviewDAO {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "rate", nullable = false, columnDefinition = "Integer")
    private Rate rate;

    @Column(name = "date_and_time_of_comment", nullable = false)
    private LocalDateTime dateAndTimeOfComment;

    @ManyToOne
    @JoinColumn(name = "student_id", table = "student_user", referencedColumnName = "id")
    private StudentUserDAO student;
    
}
