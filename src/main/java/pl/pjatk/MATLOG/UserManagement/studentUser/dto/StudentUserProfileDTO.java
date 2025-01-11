package pl.pjatk.MATLOG.userManagement.studentUser.dto;

import java.time.LocalDate;

public record StudentUserProfileDTO(String id,
                                    String firstName,
                                    String lastName,
                                    LocalDate dateOfBirth) {
}
