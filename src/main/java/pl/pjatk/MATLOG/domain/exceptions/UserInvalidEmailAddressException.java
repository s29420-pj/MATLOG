package pl.pjatk.MATLOG.domain.exceptions;

public class UserInvalidEmailAddressException extends RuntimeException {
    public UserInvalidEmailAddressException() {
        super("Provided email address is incorrect. Please make sure that you enter real email address.");
    }
}
