package pl.pjatk.MATLOG.Domain.Exceptions.UserExceptions;

public class UserInvalidRoleException extends RuntimeException {
    public UserInvalidRoleException() {
        super("Selected role doesn't exist. Please try again.");
    }
}
