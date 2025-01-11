package pl.pjatk.MATLOG.userManagement.tutorUser.mapper;

import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.PrivateLessonTutorUserDTO;

@Mapper
public class TutorUserPrivateLessonDTOMapper {

    public PrivateLessonTutorUserDTO mapToDTO(TutorUser tutorUser) {
        return new PrivateLessonTutorUserDTO(tutorUser.getId());
    }
}
