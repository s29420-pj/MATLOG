package pl.pjatk.MATLOG.Domain.Exceptions.ReviewExceptions;

public class ReviewInvalidTutorId extends RuntimeException {
    public ReviewInvalidTutorId() {
        super("Provided TutorId is not valid. Please make sure that it's not empty");
    }
}
