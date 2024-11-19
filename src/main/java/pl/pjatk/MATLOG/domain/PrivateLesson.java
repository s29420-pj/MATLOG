package pl.pjatk.MATLOG.domain;

import lombok.Getter;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.PrivateLessonInvalidEndTimeException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.PrivateLessonInvalidStartTimeException;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Concrete representation of Lesson - Private Lesson.
 * Contains all the attribute as Lesson abstract class.
 * Mandatory fields of PrivateLesson are:
 * - startTime
 * - endTime
 */
@Getter
public final class PrivateLesson extends Lesson {

    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Constructor of PrivateLesson. It calls validateFields method to check
     * if all required fields are set
     * @param builder Builder of PrivateLesson class
     */
    private PrivateLesson(PrivateLessonBuilder builder) {
        super(builder);
        this.startTime = LocalTime.from(builder.startTime);
        this.endTime = LocalTime.from(builder.endTime);
    }

    /**
     * Method that checks if all required fields are set by builder
     * @param builder Builder
     * @throws PrivateLessonInvalidStartTimeException when startTime is null
     * @throws PrivateLessonInvalidEndTimeException when endTime is null
     */
    private void validateFields(PrivateLessonBuilder builder) {
        if (builder.startTime == null) throw new PrivateLessonInvalidStartTimeException();
        if (builder.endTime == null) throw new PrivateLessonInvalidEndTimeException();
    }

    /**
     * Method that returns builder and starts chaining creation of the object
     * @return PrivateLessonBuilder
     */
    public static PrivateLessonBuilder builder() {
        return new PrivateLessonBuilder();
    }
    /**
     * Concrete representation of the builder in Lesson abstract class
     */
    public static class PrivateLessonBuilder extends Builder<PrivateLessonBuilder> {

        private LocalDateTime startTime;
        private LocalDateTime endTime;

        /**
         * Method that sets PrivateLesson's start time
         * @param startTime Time and date when private lesson begins
         * @return PrivateLessonBuilder
         * @throws PrivateLessonInvalidStartTimeException when startTime is before or equal to present
         */
        public PrivateLessonBuilder withStartTime(LocalDateTime startTime) {
            if (startTime.isBefore(LocalDateTime.now()) || startTime.isEqual(LocalDateTime.now())) {
                throw new PrivateLessonInvalidStartTimeException();
            }
            this.startTime = startTime;
            return self();
        }

        /**
         * Method that sets PrivateLesson's end time
         * @param endTime Time and date when private lesson ends
         * @return PrivateLessonBuilder
         * @throws PrivateLessonInvalidEndTimeException when end time before present and start time
         * or when is equal to start time
         */
        public PrivateLessonBuilder withEndTime(LocalDateTime endTime) {
            if (endTime.isBefore(LocalDateTime.now()) || endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
                throw new PrivateLessonInvalidEndTimeException();
            }
            this.endTime = endTime;
            return self();
        }

        @Override
        protected PrivateLessonBuilder self() {
            return this;
        }

        @Override
        protected PrivateLesson build() {
            return new PrivateLesson(this);
        }
    }
}
