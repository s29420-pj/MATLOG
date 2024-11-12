package pl.pjatk.MATLOG.domain;

public final class PrivateLesson extends Lesson {

    public static class PrivateLessonBuilder extends Builder<PrivateLessonBuilder> {

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
