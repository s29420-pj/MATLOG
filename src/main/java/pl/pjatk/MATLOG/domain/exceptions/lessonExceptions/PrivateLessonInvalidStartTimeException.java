package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class PrivateLessonInvalidStartTimeException extends RuntimeException {
    public PrivateLessonInvalidStartTimeException() {
        super("Start time cannot be set in the past nor the present. Please make sure that " +
                "your provide future start time.");
    }
}
