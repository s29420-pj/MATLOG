package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class PrivateLessonInvalidIdException extends RuntimeException {
    public PrivateLessonInvalidIdException() {
        super("Provided tutor or student does not exist. Please try again.");
    }

    public PrivateLessonInvalidIdException(String message) {
        super(message);
    }
}
