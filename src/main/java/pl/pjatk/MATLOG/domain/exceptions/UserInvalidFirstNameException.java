package pl.pjatk.MATLOG.domain.exceptions;

public class UserInvalidFirstNameException extends RuntimeException {
  public UserInvalidFirstNameException() {
    super("Provided first name is incorrect. Please make sure that you enter real first name.");
  }
}
