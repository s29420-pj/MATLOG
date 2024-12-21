package pl.pjatk.MATLOG.Domain;

import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.Domain.Exceptions.ReviewExceptions.ReviewInvalidRateException;
import pl.pjatk.MATLOG.Domain.Exceptions.ReviewExceptions.ReviewInvalidStudentId;
import pl.pjatk.MATLOG.Domain.Exceptions.ReviewExceptions.ReviewInvalidTutorId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Class representing Review of the Tutor in application.
 * It can be created by Student who attended to any kind of Lesson that was led by tutor.
 */
public final class Review {
    private final String id;
    private final String comment;
    private final Stars rate;
    private final LocalDateTime dateAndTimeOfComment;
    private final String studentId;
    private final String tutorId;

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Stars getRate() {
        return rate;
    }

    public LocalDateTime getDateAndTimeOfComment() {
        return dateAndTimeOfComment;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String comment;
        private Stars rate;
        private LocalDateTime dateAndTimeOfComment;
        private String studentId;
        private String tutorId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder withRate(Stars rate) {
            this.rate = rate;
            return this;
        }

        public Builder withDateAndTimeOfComment(LocalDateTime dateAndTime) {
            this.dateAndTimeOfComment = dateAndTime;
            return this;
        }

        public Builder withStudentId(String studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder withTutorId(String tutorId) {
            this.tutorId = tutorId;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

    private Review(Builder builder) {
        if (builder.id == null || builder.id.isEmpty())
            this.id = UUID.randomUUID().toString();
        else this.id = builder.id;
        this.comment = Objects.requireNonNullElseGet(builder.comment, String::new);
        this.rate = Objects.requireNonNull(builder.rate);
        this.dateAndTimeOfComment = Objects.requireNonNullElseGet(builder.dateAndTimeOfComment, LocalDateTime::now);
        this.studentId = Objects.requireNonNull(builder.studentId);
        this.tutorId = Objects.requireNonNull(builder.tutorId);
    }
}
