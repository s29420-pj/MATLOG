package pl.pjatk.MATLOG.userManagement.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import pl.pjatk.MATLOG.domain.enums.Role;

import java.time.LocalDate;

/**
 * Record which tells what data must be considered in terms of registration.
 * It's used by jackson to serialize and deserialize data.
 * @param firstName First name of the user
 * @param lastName last name of the user
 * @param emailAddress email address of the user
 * @param password password of the user
 * @param dateOfBirth date of birth of the user
 * @param role role of the user (i.e. StudentUser or TutorUser)
 */
public record UserRegistrationDTO(String firstName,
                                  String lastName,
                                  String emailAddress,
                                  String password,
                                  LocalDate dateOfBirth,
                                  Role role) {
    @JsonCreator
    public UserRegistrationDTO {
    }
}
