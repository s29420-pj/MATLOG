package pl.pjatk.MATLOG.domain;

import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

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
    private final Rate rate;
    private final LocalDateTime dateAndTimeOfComment;
    private final StudentUserReviewLookUpDTO studentUser;

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Rate getRate() {
        return rate;
    }

    public LocalDateTime getDateAndTimeOfComment() {
        return dateAndTimeOfComment;
    }

    public StudentUserReviewLookUpDTO getStudentUser() {
        return studentUser;
    }

    /**
     * Constructor which is used by the builder.
     * If id is not provided in builder then random UUID is generated.
     * If comment is not provided in builder then new empty string is created.
     * If dateAndTimeOfComment is not provided in builder then LocalDateTime.now() is instantiated.
     * @param builder created builder
     * @throws NullPointerException if rate or student or tutor is null
     */
    private Review(Builder builder) {
        if (builder.id == null || builder.id.isEmpty())
            this.id = UUID.randomUUID().toString();
        else this.id = builder.id;
        this.comment = Objects.requireNonNullElseGet(builder.comment, String::new);
        this.rate = Objects.requireNonNull(builder.rate);
        this.dateAndTimeOfComment = Objects.requireNonNullElseGet(builder.dateAndTimeOfComment, LocalDateTime::now);
        this.studentUser = Objects.requireNonNull(builder.studentUser);
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
        private Rate rate;
        private LocalDateTime dateAndTimeOfComment;
        private StudentUserReviewLookUpDTO studentUser;

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
         * @param rate Rate Enum
         * @return Builder
         */
        public Builder withRate(Rate rate) {
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
         * Sets review's student
         * @param studentUser Identifier of a student, who created this review.
         * @return Builder
         */
        public Builder withStudent(StudentUserReviewLookUpDTO studentUser) {
            this.studentUser = studentUser;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
