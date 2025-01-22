package pl.pjatk.MATLOG.userManagement.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.pjatk.MATLOG.domain.exceptions.userExceptions.*;
import pl.pjatk.MATLOG.reviewManagement.exceptions.ReviewNotFoundException;
import pl.pjatk.MATLOG.userManagement.exceptions.InvalidPasswordException;
import pl.pjatk.MATLOG.userManagement.exceptions.TutorUserNotFoundException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.domain.exceptions.reviewExceptions.*;
import pl.pjatk.MATLOG.userManagement.user.dto.ErrorDTO;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(TutorUserNotFoundException.class)
    public ResponseEntity<String> handleTutorUserNotFoundException(TutorUserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<String> handleReviewNotFoundException(ReviewNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ReviewInvalidRateException.class)
    public ResponseEntity<String> handleReviewInvalidRateException(ReviewInvalidRateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ReviewInvalidStudentId.class)
    public ResponseEntity<String> handleReviewInvalidStudentId(ReviewInvalidStudentId ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ReviewInvalidTutorId.class)
    public ResponseEntity<String> handleReviewInvalidTutorId(ReviewInvalidTutorId ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserEmptyPasswordException.class)
    public ResponseEntity<String> handleUserEmptyPasswordException(UserEmptyPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserInvalidDateOfBirthException.class)
    public ResponseEntity<String> handleUserInvalidDateOfBirthException(UserInvalidDateOfBirthException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserInvalidEmailAddressException.class)
    public ResponseEntity<String> handleUserInvalidEmailAddressException(UserInvalidEmailAddressException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserInvalidFirstNameException.class)
    public ResponseEntity<String> handleUserInvalidFirstNameException(UserInvalidFirstNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserInvalidLastNameException.class)
    public ResponseEntity<String> handleUserInvalidLastNameException(UserInvalidLastNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserInvalidRoleException.class)
    public ResponseEntity<String> handleUserInvalidRoleException(UserInvalidRoleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserUnsecurePasswordException.class)
    public ResponseEntity<String> handleUserUnsecurePasswordException(UserUnsecurePasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorDTO(ex.getMessage()));
    }


}
