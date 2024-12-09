package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class PrivateLessonInvalidSchoolSubjectException extends RuntimeException {
    public PrivateLessonInvalidSchoolSubjectException() {
        super("Provided subjects are not valid. Please make sure that your enter valid subjects.");
    }
}
