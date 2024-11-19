package pl.pjatk.MATLOG.domain;

import lombok.Getter;
import pl.pjatk.MATLOG.domain.exceptions.reviewExceptions.ReviewInvalidRateException;
import pl.pjatk.MATLOG.domain.exceptions.reviewExceptions.ReviewInvalidStudentId;
import pl.pjatk.MATLOG.domain.exceptions.reviewExceptions.ReviewInvalidTutorId;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Class representing Review of the Tutor in application.
 * It can be created by Student who attended to any kind of Lesson that was led by tutor.
 */
@Getter
public final class Review {
    private final String id;
    private final String comment;
    private final Stars rate;
    private final LocalDateTime dateAndTimeOfComment;
    private final String studentId;
    private final String tutorId;

    /**
     * Static factory method that returns review.
     * @param rate Overall rate of the tutor
     * @param comment Detailed information. If equals null then it's converted to empty String
     * @param studentId Student Identification that tells who created the review
     * @param tutorId Tutor Identification that tells who have been reviewed
     * @return Review
     * @throws ReviewInvalidRateException - When rate is empty
     * @throws ReviewInvalidStudentId - When studentId is empty
     * @throws ReviewInvalidTutorId - When tutorId is empty
     */
    public static Review create(Stars rate, String comment, String studentId, String tutorId) {
        if (rate == null) {
            throw new ReviewInvalidRateException();
        }
        comment = comment == null ? "" : comment;
        if (studentId == null || studentId.isBlank()) {
            throw new ReviewInvalidStudentId();
        }
        if (tutorId == null || tutorId.isBlank()) {
            throw new ReviewInvalidTutorId();
        }
        return new Review(rate, comment, studentId, tutorId);
    }

    /**
     * Overloaded static method that uses its full form to validate every parameter.
     * @param rate Overall rate of the tutor
     * @param studentId Student Identification that tells who created the review
     * @param tutorId Tutor Identification that tells who have been reviewed
     * @return Review
     */
    public static Review create(Stars rate, String studentId, String tutorId) {
        return create(rate, null, studentId, tutorId);
    }

    /**
     * Static factory method that creates review from provided different review.
     * It uses create static method to include all checks-up.
     * @param review - original review
     * @return Review
     */
    public static Review from(Review review) {
        return create(review.rate, review.comment, review.studentId, review.tutorId);
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
