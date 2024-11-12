package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class LessonInvalidTitleException extends RuntimeException {
    public LessonInvalidTitleException() {
        super("Provided title is not valid. Please make sure that your enter valid title.");
    }
}
