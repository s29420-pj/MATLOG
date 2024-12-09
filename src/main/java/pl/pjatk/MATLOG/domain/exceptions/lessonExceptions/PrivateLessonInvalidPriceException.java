package pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions;

public class PrivateLessonInvalidPriceException extends RuntimeException {
    public PrivateLessonInvalidPriceException() {
        super("Lesson can be free, but price of the lesson cannot be below 0.");
    }

    public PrivateLessonInvalidPriceException(String message) {
        super(message);
    }
}
