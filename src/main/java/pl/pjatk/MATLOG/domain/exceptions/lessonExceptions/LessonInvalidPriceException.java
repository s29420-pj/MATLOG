package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class LessonInvalidPriceException extends RuntimeException {
    public LessonInvalidPriceException() {
        super("Price of the lesson cannot be below 0.");
    }

    public LessonInvalidPriceException(String message) {
        super(message);
    }
}
