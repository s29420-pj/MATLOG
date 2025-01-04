package pl.pjatk.MATLOG.UserManagement.user.dto;

import pl.pjatk.MATLOG.Domain.Enums.Role;

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
