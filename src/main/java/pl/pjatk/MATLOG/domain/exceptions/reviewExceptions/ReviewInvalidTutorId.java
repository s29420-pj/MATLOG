package pl.pjatk.MATLOG.domain.exceptions.reviewExceptions;

public class ReviewInvalidTutorId extends RuntimeException {
    public ReviewInvalidTutorId() {
        super("Provided TutorId is not valid. Please make sure that it's not empty");
    }
}
