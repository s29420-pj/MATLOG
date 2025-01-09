package pl.pjatk.MATLOG.userManagement.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User with that email address already exists.\n" +
                "Please try with different email address.");
    }
}
