package pl.pjatk.MATLOG.userManagement.tutorUser.persistance;

import jakarta.persistence.*;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;

import java.time.LocalDateTime;

@Entity(name = "review")
public class ReviewDAO {

    @Id
    String id;

    @Column(nullable = false)
    String comment;

    @Column(nullable = false)
    Rate rate;

    @Column(nullable = false)
    LocalDateTime dateAndTimeOfReview;

    @ManyToOne
    @JoinColumn
    StudentUserDAO student;

    protected ReviewDAO() {
    }

    ReviewDAO(String id,
              String comment,
              Rate rate,
              LocalDateTime dateAndTimeOfReview,
              StudentUserDAO student) {
        this.id = id;
        this.comment = comment;
        this.rate = rate;
        this.dateAndTimeOfReview = dateAndTimeOfReview;
        this.student = student;
    }
}
