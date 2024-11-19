package pl.pjatk.MATLOG.domain.exceptions.lessonExceptions;

public class LessonInvalidPriceException extends RuntimeException {
    public LessonInvalidPriceException() {
        super("Lesson can be free, but price of the lesson cannot be below 0.");
    }
}
