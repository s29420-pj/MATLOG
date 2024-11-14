package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class LessonInvalidOwnerIdException extends RuntimeException {
    public LessonInvalidOwnerIdException() {
        super("Provided tutor does not exist. Please try again.");
    }
}
