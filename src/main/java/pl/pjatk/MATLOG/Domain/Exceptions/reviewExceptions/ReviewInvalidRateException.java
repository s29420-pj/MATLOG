package pl.pjatk.MATLOG.Domain.Exceptions.ReviewExceptions;

public class ReviewInvalidRateException extends RuntimeException {
    public ReviewInvalidRateException() {
        super("Rate have to be included. Please enter rate");
    }
}
