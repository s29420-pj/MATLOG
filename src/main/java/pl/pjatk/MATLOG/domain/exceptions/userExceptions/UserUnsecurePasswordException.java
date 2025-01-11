package pl.pjatk.MATLOG.domain.exceptions.userExceptions;

public class UserUnsecurePasswordException extends RuntimeException {
    public UserUnsecurePasswordException() {
        super("Provided password is unsecure. Please be sure that your password has at least one " +
                "capital letter, one special sign and is at least 6 characters long.");
    }
}
