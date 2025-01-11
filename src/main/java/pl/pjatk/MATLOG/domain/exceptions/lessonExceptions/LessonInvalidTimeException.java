package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class LessonInvalidTimeException extends RuntimeException {
    public LessonInvalidTimeException() {
        super("Provided time is not valid. Please make sure that lesson isn't ending in the past.");
    }
}
