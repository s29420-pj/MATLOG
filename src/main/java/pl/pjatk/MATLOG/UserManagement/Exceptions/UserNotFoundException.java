package pl.pjatk.MATLOG.UserManagement.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User with provided username doesn't exist. Please try again.");
    }
}
