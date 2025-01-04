package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class PrivateLessonInvalidStatusException extends RuntimeException {
    public PrivateLessonInvalidStatusException() {
        super("Provided status is invalid. Please try again.");
    }

    public PrivateLessonInvalidStatusException(String message) {
        super(message);
    }
}
