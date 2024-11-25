package pl.pjatk.MATLOG.userManagement.exceptions;

public class UserInvalidEmailAddressException extends RuntimeException {
    public UserInvalidEmailAddressException() {
        super("Error occured. Please make sure that you have entered correct email address.");
    }
}
