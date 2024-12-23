package pl.pjatk.MATLOG.domain;

import pl.pjatk.MATLOG.domain.enums.Stars;

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

    /**
     * Constructor which is used by the builder.
     * If id is not provided in builder then random UUID is generated.
     * If comment is not provided in builder then new empty string is created.
     * If dateAndTimeOfComment is not provided in builder then LocalDateTime.now() is instantiated.
     * @param builder created builder
     * @throws NullPointerException if rate or studentId or tutorId is null
     */
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

    /**
     * Handy method to easily start chaining builder class.
     * @return Builder
     */
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

        /**
         * Sets Review's id.
         * @param id String representation of an id.
         * @return Builder
         */
        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets Review's comment.
         * @param comment String representation of evaluation.
         * @return Builder
         */
        public Builder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        /**
         * Sets Review's rate.
         * @param rate Stars Enum
         * @return Builder
         */
        public Builder withRate(Stars rate) {
            this.rate = rate;
            return this;
        }

        /**
         * Sets review's date and time
         * @param dateAndTime LocalDateTime when review was added.
         * @return Builder
         */
        public Builder withDateAndTimeOfReview(LocalDateTime dateAndTime) {
            this.dateAndTimeOfComment = dateAndTime;
            return this;
        }

        /**
         * Sets review's studentId
         * @param studentId Identifier of a student, who created this review.
         * @return Builder
         */
        public Builder withStudentId(String studentId) {
            this.studentId = studentId;
            return this;
        }

        /**
         * Sets review tutorId.
         * @param tutorId Identifier of a tutor
         * @return Builder
         */
        public Builder withTutorId(String tutorId) {
            this.tutorId = tutorId;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
