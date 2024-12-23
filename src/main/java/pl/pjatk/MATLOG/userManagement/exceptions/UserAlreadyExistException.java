package pl.pjatk.MATLOG.userManagement.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("User with provided email address already exists.");
    }
}
