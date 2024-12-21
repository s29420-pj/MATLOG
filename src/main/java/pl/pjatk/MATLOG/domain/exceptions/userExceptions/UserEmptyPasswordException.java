package pl.pjatk.MATLOG.Domain.Exceptions.UserExceptions;

public class UserEmptyPasswordException extends RuntimeException {
    public UserEmptyPasswordException() {
        super("Password cannot be empty. Please provide valid password");
    }
}
