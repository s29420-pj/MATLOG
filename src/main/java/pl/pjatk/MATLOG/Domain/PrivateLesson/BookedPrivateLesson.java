package pl.pjatk.MATLOG.Domain.PrivateLesson;

import lombok.Getter;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.Domain.StudentUser;

import java.util.Objects;

@Getter
public final class BookedPrivateLesson extends PrivateLesson {

    private final StudentUser studentUser;
    private final String connectionCode;
    private boolean isPaid;

    private BookedPrivateLesson(BookedPrivateLessonBuilder builder) {
        super(builder);
        this.studentUser = builder.studentUser;
        this.connectionCode = builder.connectionCode;
        this.isPaid = Objects.requireNonNullElse(builder.isPaid, false);
    }

    public static BookedPrivateLessonBuilder builder() {
        return new BookedPrivateLessonBuilder();
    }

    @Override
    public PrivateLessonStatus getStatus() {
        if (isPaid) return PrivateLessonStatus.PAID;
        return PrivateLessonStatus.BOOKED;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public static class BookedPrivateLessonBuilder extends Builder<BookedPrivateLessonBuilder> {

        private StudentUser studentUser;
        private String connectionCode;
        private boolean isPaid;

        public BookedPrivateLessonBuilder withStudentUser(StudentUser studentUser) {
            this.studentUser = studentUser;
            return self();
        }

        public BookedPrivateLessonBuilder withConnectionCode(String connectionCode) {
            this.connectionCode = connectionCode;
            return self();
        }

        public BookedPrivateLessonBuilder withIsPaid(boolean isPaid) {
            this.isPaid = isPaid;
            return self();
        }

        @Override
        protected BookedPrivateLessonBuilder self() {
            return this;
        }

        @Override
        public BookedPrivateLesson build() {
            return new BookedPrivateLesson(this);
        }
    }
}
