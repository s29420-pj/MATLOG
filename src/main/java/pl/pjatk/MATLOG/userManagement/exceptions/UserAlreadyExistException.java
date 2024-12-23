package pl.pjatk.MATLOG.UserManagement.Exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("User with provided email address already exists.");
    }
}
