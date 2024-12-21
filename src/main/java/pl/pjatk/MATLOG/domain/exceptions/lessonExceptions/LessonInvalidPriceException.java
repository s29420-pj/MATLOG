package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class LessonInvalidPriceException extends RuntimeException {
    public LessonInvalidPriceException() {
        super("Price of the lesson cannot be below 0.");
    }

    public LessonInvalidPriceException(String message) {
        super(message);
    }
}
