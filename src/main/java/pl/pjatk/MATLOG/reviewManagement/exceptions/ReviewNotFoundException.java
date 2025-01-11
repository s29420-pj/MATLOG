package pl.pjatk.MATLOG.reviewManagement.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super("Review with provided id has not been found.");
    }
}
