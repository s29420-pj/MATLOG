package pl.pjatk.MATLOG.UserManagement.user.persistance;

import pl.pjatk.MATLOG.Domain.Enums.Role;

import java.time.LocalDate;

public interface UserDTO {
    Role role();
    String firstName();
    String lastName();
    String emailAddress();
    String password();
    LocalDate dateOfBirth();
}
