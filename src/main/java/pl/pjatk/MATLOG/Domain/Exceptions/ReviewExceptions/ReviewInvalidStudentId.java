package pl.pjatk.MATLOG.Domain.Exceptions.ReviewExceptions;

public class ReviewInvalidStudentId extends RuntimeException {
    public ReviewInvalidStudentId() {
        super("Provided StudentId is invalid. Please make sure that it's not empty");
    }
}
