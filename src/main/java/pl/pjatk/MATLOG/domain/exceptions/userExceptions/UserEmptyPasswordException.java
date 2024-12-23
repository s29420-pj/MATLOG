package pl.pjatk.MATLOG.domain.exceptions.userExceptions;

public class UserEmptyPasswordException extends RuntimeException {
    public UserEmptyPasswordException() {
        super("Password cannot be empty. Please provide valid password");
    }
}
