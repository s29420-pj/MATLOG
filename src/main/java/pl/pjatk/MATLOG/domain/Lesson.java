package pl.pjatk.MATLOG.domain;

import lombok.Getter;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidDateException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidTimeException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidTitleException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Abstract class that represents all kind of Lessons in the application.
 * It must be extended by classes that want to be considered as Lessons.
 *
 */
@Getter
public abstract class Lesson {
    private final String id;
    private final String ownerId;
    private final String title;
    private final String description;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final double price;

    /**
     * Object of Lesson can be created only with Builder.
     * Every concrete class have to implement it.
     * @param <T>
     */
    abstract static class Builder<T extends Builder<T>> {
        private String ownerId;
        private String title;
        private String description;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private double price;

        /**
         * Builder of Lesson class. It contains all needed attributes to create lesson
         * @param ownerId - Identification of the owner
         * @param date - Date (YYYY/mm/DD) of lesson
         * @param startTime - Start time (HH:mm:ss) of lesson
         * @param endTime - End time (HH:mm:ss) of lesson
         * @throws LessonInvalidDateException - When date is null or is before today's date
         * @throws LessonInvalidTimeException - When date is now and start time is before time in the present or
         * when start time is after end time.
         */
        public Builder(String ownerId, LocalDate date, LocalTime startTime, LocalTime endTime) {
            this.ownerId = ownerId;
            if (date == null || date.isBefore(LocalDate.now())) {
                throw new LessonInvalidDateException();
            }
            this.date = date;
            if (date.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now())) {
                throw new LessonInvalidTimeException();
            }
            this.startTime = startTime;
            if (startTime.isAfter(endTime)) {
                throw new LessonInvalidTimeException();
            }
            this.endTime = endTime;
        }

        /**
         * Method that sets Lesson's title
         * @param title - Title of the lesson
         * @return Builder
         */
        public T withTitle(String title) {
            if (title == null || title.isBlank()) {
                throw new LessonInvalidTitleException();
            }
            this.title = title;
            return self();
        }

        /**
         * Method that sets Lesson's description
         * @param description - Description of the lesson
         * @return Builder
         */
        public T withDescription(String description) {
            this.description = description;
            return self();
        }

        /**
         * Method that sets Lesson's price
         * @param price - Price of the lesson
         * @return Builder
         */
        public T withPrice(double price) {
            this.price = price;
            return self();
        }

        abstract T self();

        /**
         * Method that builds up Lesson object from provided attributes
         * @return Lesson object
         */
        protected abstract Lesson build();
    }

    protected Lesson(Builder<?> builder) {
        this.id = UUID.randomUUID().toString();
        this.ownerId = builder.ownerId;
        this.title = builder.title;
        this.description = builder.description;
        this.date = builder.date;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.price = builder.price;
    }



}
