package pl.pjatk.MATLOG.Domain.PrivateLesson;

import lombok.Getter;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.Domain.TutorUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Class representing unassigned Private Lesson in application.
 * Private Lesson is a lesson that is taught by Tutor to Student.
 * It can be created by Tutor to create slot that can be booked by Student.
 */
@Getter
public abstract class PrivateLesson {

    private final String id;
    private final TutorUser tutor;
    private final boolean isAvailableOffline;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Double price;

    public abstract PrivateLessonStatus getStatus();

    protected PrivateLesson(Builder<?> builder) {
        if (builder.id == null || builder.id.isEmpty()) this.id = UUID.randomUUID().toString();
        else this.id = builder.id;
        this.tutor = builder.tutor;
        this.isAvailableOffline = builder.isAvailableOffline;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.price = builder.price;
    }

    /**
     * Abstract builder that have to be extended in concrete User class
     * @param <T>
     */
    public abstract static class Builder<T extends Builder<T>> {
        private String id;
        private TutorUser tutor;
        private boolean isAvailableOffline;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double price;

        /**
         * Method that sets id of the PrivateLesson.
         * @param id Identifier of the PrivateLesson.
         * @return Builder
         */
        public T withId(String id) {
            this.id = id;
            return self();
        }

        /**
         * Method that sets tutor of the PrivateLesson.
         * @param tutor Tutor of the PrivateLesson.
         * @return Builder
         */
        public T withTutor(TutorUser tutor) {
            this.tutor = tutor;
            return self();
        }

        /**
         * Method that sets isAvailableOffline field of the PrivateLesson.
         * @param isAvailableOffline boolean that indicates if lesson is available offline.
         * @return Builder
         */
        public T withIsAvailableOffline(boolean isAvailableOffline) {
            this.isAvailableOffline = isAvailableOffline;
            return self();
        }

        /**
         * Method that sets start time of the PrivateLesson.
         * @param startTime Start time of the PrivateLesson.
         * @return Builder
         */
        public T withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return self();
        }

        /**
         * Method that sets end time of the PrivateLesson.
         * @param endTime End time of the PrivateLesson.
         * @return Builder
         */
        public T withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return self();
        }

        /**
         * Method that sets price of the PrivateLesson.
         * @param price Price of the PrivateLesson.
         * @return Builder
         */
        public T withPrice(Double price) {
            this.price = price;
            return self();
        }

        /**
         * Method that needs to be implemente by concrete builder static class in the concrete User class.
         * It must return builder class.
         * @return Builder
         */
        abstract T self();

        /**
         * Method that builds an objects with provided data and calls methods
         * that validates if all the fields are provided.
         * @return PrivateLesson
         */
        protected abstract PrivateLesson build();

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
//            throw new PrivateLessonInvalidEndTimeException();
//    }
}