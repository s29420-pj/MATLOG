package pl.pjatk.MATLOG.domain.exceptions.userExceptions;

public class UserAgeRestrictionException extends RuntimeException {
    public UserAgeRestrictionException() {
        super("We're sorry, but you are too young to register. You must be at least 13 years old");
    }
}
