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
        validateFields(builder);
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
        if (builder.startTime == null) throw new IllegalStateException("Start time of private lesson is mandatory and must be set");
        if (builder.endTime == null) throw new IllegalStateException("End time of private lesson is mandatory and must be set");

        LocalDateTime builderStartTime = builder.startTime;
        LocalDateTime builderEndTime = builder.endTime;
        if (builderStartTime.isBefore(LocalDateTime.now()) || builderStartTime.isEqual(LocalDateTime.now()))
            throw new PrivateLessonInvalidStartTimeException();

        if (builderEndTime.isBefore(LocalDateTime.now()) || builderEndTime.isBefore(builderStartTime) ||
                builderEndTime.isEqual(builderStartTime))

            throw new PrivateLessonInvalidEndTimeException();
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
         */
        public PrivateLessonBuilder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return self();
        }

        /**
         * Method that sets PrivateLesson's end time
         * @param endTime Time and date when private lesson ends
         * @return PrivateLessonBuilder
         */
        public PrivateLessonBuilder withEndTime(LocalDateTime endTime) {
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
