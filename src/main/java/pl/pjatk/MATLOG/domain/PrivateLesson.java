package pl.pjatk.MATLOG.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public final class PrivateLesson extends Lesson {

    public static class PrivateLessonBuilder extends Builder<PrivateLessonBuilder> {

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
