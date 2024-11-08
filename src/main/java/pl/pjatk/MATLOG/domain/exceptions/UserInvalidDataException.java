package pl.pjatk.MATLOG.domain.exceptions;

public class UserInvalidDataException extends RuntimeException {
    public UserInvalidDataException(String message) {
        super(message);
    }
}
