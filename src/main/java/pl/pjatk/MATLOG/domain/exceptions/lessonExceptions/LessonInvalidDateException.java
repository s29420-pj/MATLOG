package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class LessonInvalidDateException extends RuntimeException {
    public LessonInvalidDateException() {
        super("Provided date is not valid. Please make sure that date is set in the future");
    }
}
