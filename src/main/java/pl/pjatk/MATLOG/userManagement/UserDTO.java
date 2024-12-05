package pl.pjatk.MATLOG.userManagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import pl.pjatk.MATLOG.domain.Role;

import java.time.LocalDate;

public record UserDTO(String firstName, String lastName, String emailAddress, String password, LocalDate dateOfBirth,
                      Role role) {
    @JsonCreator
    public UserDTO {
    }
}
