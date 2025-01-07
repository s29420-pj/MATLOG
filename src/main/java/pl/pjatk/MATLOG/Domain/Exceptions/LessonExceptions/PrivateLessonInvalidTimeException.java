package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class PrivateLessonInvalidTimeException extends RuntimeException {
    public PrivateLessonInvalidTimeException() {
        super("End time cannot be set to the past nor be the same as start time" +
                " nor be before start time. Please make sure that end time is after start time and " +
                "is not set to the past.");
    }

    public PrivateLessonInvalidTimeException(String message) {
        super(message);
    }
}
