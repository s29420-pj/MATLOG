package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class PrivateLessonInvalidStatusException extends RuntimeException {
    public PrivateLessonInvalidStatusException() {
        super("Provided status is invalid. Please try again.");
    }

    public PrivateLessonInvalidStatusException(String message) {
        super(message);
    }
}
