package pl.pjatk.MATLOG.domain.exceptions.userExceptions;

public class UserInvalidRoleException extends RuntimeException {
    public UserInvalidRoleException() {
        super("Selected role doesn't exist. Please try again.");
    }
}
