package pl.pjatk.MATLOG.Domain;

import lombok.Getter;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;

import java.time.LocalDateTime;
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
    private final StudentUser student;
    private final String connectionCode;
    private final PrivateLessonStatus status;
    private final boolean isAvailableOffline;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Double price;

    private PrivateLesson(Builder builder) {
        if (builder.id == null || builder.id.isEmpty()) this.id = UUID.randomUUID().toString();
        else this.id = builder.id;
        this.tutor = builder.tutor;
        this.student = builder.student;
        this.connectionCode = builder.connectionCode;
        this.status = builder.status;
        this.isAvailableOffline = builder.isAvailableOffline;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.price = builder.price;
    }

    /**
     * Abstract builder that have to be extended in concrete User class
     */
    public abstract static class Builder {
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


    /**
     * Method that validates all fields of the PrivateLesson.
     */
//    private void validateFields() {
//        if (schoolSubjects == null) throw new PrivateLessonInvalidSchoolSubjectException();
//        if (tutorId == null || tutorId.isEmpty()) throw new PrivateLessonInvalidIdException();
//        if (status == null) throw new PrivateLessonInvalidStatusException();
//        if (startTime == null) throw new IllegalStateException("Start time of private lesson is mandatory and must be set");
//        if (endTime == null) throw new IllegalStateException("End time of private lesson is mandatory and must be set");
//        if (price == null) {
//            throw new PrivateLessonInvalidPriceException("Price of private lesson is mandatory and must be set");
//        } else if (price < 0) {
//            throw new PrivateLessonInvalidPriceException();
//        }
//
//        if (startTime.isBefore(LocalDateTime.now()))
//            throw new PrivateLessonInvalidStartTimeException();
//
//        if (endTime.isBefore(LocalDateTime.now()) || endTime.isBefore(startTime) || endTime.isEqual(startTime))
//            throw new PrivateLessonInvalidTimeException();
//    }
}