package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class LessonInvalidOwnerId extends RuntimeException {
    public LessonInvalidOwnerId() {
        super("Owner id is not valid. Please make sure that you provide real value");
    }
}
