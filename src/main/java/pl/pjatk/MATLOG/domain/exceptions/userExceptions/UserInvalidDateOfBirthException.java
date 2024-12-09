package pl.pjatk.MATLOG.Domain.Exceptions.UserExceptions;

public class UserInvalidDateOfBirthException extends RuntimeException {
    public UserInvalidDateOfBirthException() {
        super("Provided date of birth is incorrect. Please make sure that you enter real date of birth.");
    }
}
