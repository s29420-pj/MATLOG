package pl.pjatk.MATLOG.userManagement.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends RuntimeException {

    private final HttpStatus httpStatus;

    public InvalidPasswordException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
