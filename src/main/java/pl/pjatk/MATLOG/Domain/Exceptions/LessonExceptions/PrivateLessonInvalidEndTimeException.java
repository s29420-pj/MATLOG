package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class PrivateLessonInvalidEndTimeException extends RuntimeException {
    public PrivateLessonInvalidEndTimeException() {
        super("End time cannot be set to the past nor be the same as start time" +
                " nor be before start time. Please make sure that end time is after start time and " +
                "is not set to the past.");
    }
}
