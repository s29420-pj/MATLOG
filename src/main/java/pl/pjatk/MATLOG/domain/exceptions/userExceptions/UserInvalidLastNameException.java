package pl.pjatk.MATLOG.domain.exceptions.userExceptions;

public class UserInvalidLastNameException extends RuntimeException {
    public UserInvalidLastNameException() {
        super("Provided last name is incorrect. Please make sure that you enter real last name.");
    }
}
