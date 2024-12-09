package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class LessonInvalidOwnerId extends RuntimeException {
    public LessonInvalidOwnerId() {
        super("Owner id is not valid. Please make sure that you provide real value");
    }
}
