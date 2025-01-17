package pl.pjatk.MATLOG.domain;

import lombok.Getter;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.PrivateLessonInvalidPriceException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Class representing unassigned Private Lesson in application.
 * Private Lesson is a lesson that is taught by Tutor to Student.
 * It can be created by Tutor to create slot that can be booked by Student.
 */
@Getter
public final class PrivateLesson {

    private final String id;
    private final TutorUser tutor;
    private StudentUser student;
    private String connectionCode;
    private PrivateLessonStatus status;
    private boolean isAvailableOffline;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Double price;

    public void assignStudent(StudentUser student) {
        if (this.student != null) {
            throw new IllegalStateException("Student is already assigned to this lesson");
        } else {
            this.student = student;
        }
    }

    public void unassignStudent() {
        if (this.student == null) {
            throw new IllegalStateException("Student is not assigned to this lesson");
        } else {
            this.student = null;
        }
    }

    public void changeStatus(PrivateLessonStatus status) {
        this.status = status;
    }

    public void changeConnectionCode(String connectionCode) {
        if (this.connectionCode.equals("Not assigned yet")) {
            this.connectionCode = connectionCode;
        }
    }

    /**
     * PrivateLesson constructor that creates object with provided data from builder. Data is validated.
     * @param builder Builder of the PrivateLesson with set attributes
     * */
    private PrivateLesson(Builder builder) {
        if (builder.id == null || builder.id.isEmpty()) this.id = UUID.randomUUID().toString();
        else this.id = builder.id;
        this.tutor = Objects.requireNonNull(builder.tutor, "Tutor of private lesson is mandatory and must be set");
        this.student = builder.student;

        if (builder.connectionCode == null || builder.connectionCode.isEmpty()) {
            this.connectionCode = "Not assigned yet";
        } else {
            this.connectionCode = builder.connectionCode;
        }

        this.status = Objects.requireNonNull(builder.status, "Status of private lesson is mandatory and must be set");
        this.isAvailableOffline = builder.isAvailableOffline;
        this.startTime = Objects.requireNonNull(builder.startTime, "Start time of private lesson is mandatory and must be set");
        this.endTime = Objects.requireNonNull(builder.endTime, "End time of private lesson is mandatory and must be set");

        if (builder.price < 0) {
            throw new PrivateLessonInvalidPriceException();
        } else {
            this.price = Objects.requireNonNull(builder.price, "Price of private lesson is mandatory and must be set, lesson can be free but price cannot be below 0");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Abstract builder that have to be extended in concrete User class
     */
    public static class Builder {
        private String id;
        private TutorUser tutor;
        private StudentUser student;
        private String connectionCode;
        private PrivateLessonStatus status;
        private boolean isAvailableOffline;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double price;

        /**
         * Method that sets id of the PrivateLesson.
         * @param id Identifier of the PrivateLesson.
         * @return Builder
         */
        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Method that sets tutor of the PrivateLesson.
         * @param tutor Tutor of the PrivateLesson.
         * @return Builder
         */
        public Builder withTutor(TutorUser tutor) {
            this.tutor = tutor;
            return this;
        }

        public Builder withStudent(StudentUser student) {
            this.student = student;
            return this;
        }

        public Builder withConnectionCode(String connectionCode) {
            this.connectionCode = connectionCode;
            return this;
        }

        public Builder withStatus(PrivateLessonStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Method that sets isAvailableOffline field of the PrivateLesson.
         * @param isAvailableOffline boolean that indicates if lesson is available offline.
         * @return Builder
         */
        public Builder withIsAvailableOffline(boolean isAvailableOffline) {
            this.isAvailableOffline = isAvailableOffline;
            return this;
        }

        /**
         * Method that sets start time of the PrivateLesson.
         * @param startTime Start time of the PrivateLesson.
         * @return Builder
         */
        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        /**
         * Method that sets end time of the PrivateLesson.
         * @param endTime End time of the PrivateLesson.
         * @return Builder
         */
        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        /**
         * Method that sets price of the PrivateLesson.
         * @param price Price of the PrivateLesson.
         * @return Builder
         */
        public Builder withPrice(Double price) {
            this.price = price;
            return this;
        }

        /**
         * Method that builds an objects with provided data and calls methods
         * that validates if all the fields are provided.
         * @return PrivateLesson
         */
        public PrivateLesson build() {
            return new PrivateLesson(this);
        }

    }
}