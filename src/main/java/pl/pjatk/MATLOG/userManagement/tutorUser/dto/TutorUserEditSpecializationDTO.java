package pl.pjatk.MATLOG.userManagement.tutorUser.dto;

import pl.pjatk.MATLOG.domain.enums.SchoolSubject;

import java.util.Collection;

public record TutorUserEditSpecializationDTO(
        String id,
        Collection<SchoolSubject> specializations
) {
}
