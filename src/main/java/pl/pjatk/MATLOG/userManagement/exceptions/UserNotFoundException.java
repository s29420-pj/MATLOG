package pl.pjatk.MATLOG.userManagement.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User with provided username doesn't exist. Please try again.");
    }
}
