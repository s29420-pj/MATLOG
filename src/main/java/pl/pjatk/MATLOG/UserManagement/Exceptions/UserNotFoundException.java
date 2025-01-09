package pl.pjatk.MATLOG.userManagement.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User with that email address does not exist.");
    }
}
