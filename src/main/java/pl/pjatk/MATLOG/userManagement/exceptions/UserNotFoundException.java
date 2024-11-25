package pl.pjatk.MATLOG.userManagement.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User with served username doesn't exist. Please try again.");
    }
}
