package pl.pjatk.MATLOG.domain;

import lombok.Getter;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidDateException;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.LessonInvalidTitleException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

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

    abstract static class Builder<T extends Builder<T>> {
        private String ownerId;
        private String title;
        private String description;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private double price;

        public Builder(String ownerId, LocalDate date, LocalTime startTime, LocalTime endTime) {
            this.ownerId = ownerId;
            if (date == null || date.isBefore(LocalDate.now())) {
                throw new LessonInvalidDateException();
            }
            this.date = date;
            if (date.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now())) {
                throw new LessonInvalidDateException();
            }
            this.startTime = startTime;
            if (startTime.isAfter(endTime)) {
                throw new LessonInvalidDateException();
            }
            this.endTime = endTime;
        }

        public T withTitle(String title) {
            if (title == null || title.isBlank()) {
                throw new LessonInvalidTitleException();
            }
            this.title = title;
            return self();
        }

        public T withDescription(String description) {
            this.description = description;
            return self();
        }

        public T withPrice(double price) {
            this.price = price;
            return self();
        }

        abstract T self();

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
