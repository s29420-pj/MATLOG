package pl.pjatk.MATLOG.userManagement.tutorUser.dto;

import pl.pjatk.MATLOG.domain.enums.SchoolSubject;

import java.util.Set;

public record TutorUserProfileDTO(String id,
                                  String firstName,
                                  String lastName,
                                  String biography,
                                  Set<SchoolSubject> specializations,
                                  Set<ReviewDTO> reviews) {
}
