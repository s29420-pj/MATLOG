package pl.pjatk.MATLOG.userManagement.exceptions;

public class StudentUserNotFoundException extends RuntimeException {
    public StudentUserNotFoundException() {
        super("Student with that id has not been found.");
    }
}
