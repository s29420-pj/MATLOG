package pl.pjatk.MATLOG.domain.exceptions.ReviewExceptions;

public class ReviewInvalidStudentId extends RuntimeException {
    public ReviewInvalidStudentId() {
        super("Provided StudentId is invalid. Please make sure that it's not empty");
    }
}
