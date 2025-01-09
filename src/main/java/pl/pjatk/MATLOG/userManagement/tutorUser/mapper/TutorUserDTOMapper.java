package pl.pjatk.MATLOG.userManagement.tutorUser.mapper;

import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.PrivateLessonTutorUserDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

@Mapper
public class TutorUserDTOMapper {

    private final UserPasswordValidator userPasswordValidator;

    public TutorUserDTOMapper(UserPasswordValidator userPasswordValidator) {
        this.userPasswordValidator = userPasswordValidator;
    }

    public TutorUser mapToDomain(UserRegistrationDTO userDTO) {
        return TutorUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password(), userPasswordValidator)
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }

    public TutorUser mapToDomain(PrivateLessonTutorUserDTO privateLessonTutorUserDTO) {
        return TutorUser.builder()
                .withId(privateLessonTutorUserDTO.id())
                .build();
    }
}
