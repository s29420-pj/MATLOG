package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class PrivateLessonInvalidIdException extends RuntimeException {
    public PrivateLessonInvalidIdException() {
        super("Provided tutor or student does not exist. Please try again.");
    }

    public PrivateLessonInvalidIdException(String message) {
        super(message);
    }
}
