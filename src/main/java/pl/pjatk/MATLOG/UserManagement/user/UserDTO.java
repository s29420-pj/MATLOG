package pl.pjatk.MATLOG.UserManagement.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import pl.pjatk.MATLOG.Domain.Enums.Role;

import java.time.LocalDate;

public record UserDTO(String firstName, String lastName, String emailAddress, String password, LocalDate dateOfBirth,
                      Role role) {
    @JsonCreator
    public UserDTO {
    }
}
