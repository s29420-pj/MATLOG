package pl.pjatk.MATLOG.userManagement.user.dto;

import pl.pjatk.MATLOG.domain.enums.Role;

import java.time.LocalDate;

/**
 * Interface which represents how minimal UserDTO should look like.
 */
public interface UserDTO {
    Role role();
    String firstName();
    String lastName();
    String emailAddress();
    String password();
    LocalDate dateOfBirth();
}
