package pl.pjatk.MATLOG.Domain.PrivateLesson;

import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;

public final class AvailablePrivateLesson extends PrivateLesson {

    public static AvailablePrivateLessonBuilder builder() {
        return new AvailablePrivateLessonBuilder();
    }

    @Override
    public PrivateLessonStatus getStatus() {
        return PrivateLessonStatus.AVAILABLE;
    }

    public static class AvailablePrivateLessonBuilder extends Builder<AvailablePrivateLessonBuilder> {

        @Override
        protected AvailablePrivateLessonBuilder self() {
            return this;
        }

        @Override
        public AvailablePrivateLesson build() {
            return new AvailablePrivateLesson(this);
        }
    }

    private AvailablePrivateLesson(AvailablePrivateLessonBuilder builder) {
        super(builder);
    }

}
