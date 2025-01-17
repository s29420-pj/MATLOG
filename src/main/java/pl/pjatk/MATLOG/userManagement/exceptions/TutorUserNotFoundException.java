package pl.pjatk.MATLOG.userManagement.exceptions;

public class TutorUserNotFoundException extends RuntimeException {
    public TutorUserNotFoundException() {
        super("Tutor with that id has not been found.");
    }
}
