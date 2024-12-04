package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class PrivateLessonInvalidSchoolSubjectException extends RuntimeException {
    public PrivateLessonInvalidSchoolSubjectException() {
        super("Provided subjects are not valid. Please make sure that your enter valid subjects.");
    }
}
