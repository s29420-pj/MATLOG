package pl.pjatk.MATLOG.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Concrete representation of Lesson - Private Lesson.
 * Contains all the attribute as Lesson abstract class
 */
public final class PrivateLesson extends Lesson {

    /**
     * Concrete representation of the builder in Lesson abstract class
     */
    public static class PrivateLessonBuilder extends Builder<PrivateLessonBuilder> {

        /**
         * Constructor of builder that takes must-have parameters.
         * @param ownerId - Identification of the owner of Private Lesson (Tutor)
         * @param date - Date in which the lesson takes place
         * @param startTime - Time in which lesson starts
         * @param endTime - Time in which lesson ends
         */
        public PrivateLessonBuilder(String ownerId, LocalDate date, LocalTime startTime, LocalTime endTime) {
            super(ownerId, date, startTime, endTime);
        }

        @Override
        PrivateLessonBuilder self() {
            return this;
        }

        @Override
        protected PrivateLesson build() {
            return new PrivateLesson(this);
        }
    }

    private PrivateLesson(PrivateLessonBuilder builder) {
        super(builder);
    }
}
