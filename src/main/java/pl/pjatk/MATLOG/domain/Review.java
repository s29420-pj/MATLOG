package pl.pjatk.MATLOG.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public final class Review {
    private final String id;
    private final String comment;
    private final Stars rate;
    private final LocalDateTime dateAndTimeOfComment;
    private final String studentId;
    private final String tutorId;

    public static Review create(Stars rate, String comment, String studentId, String tutorId) {
        return new Review(rate, comment, studentId, tutorId);
    }

    public static Review from(Review review) {
        return new Review(review.rate, review.comment, review.studentId, review.tutorId);
    }

    private Review(Stars rate, String comment, String studentId, String tutorId) {
        this.id = UUID.randomUUID().toString();
        this.comment = comment;
        this.rate = rate;
        this.dateAndTimeOfComment = LocalDateTime.now();
        this.studentId = studentId;
        this.tutorId = tutorId;
    }

}
