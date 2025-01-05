package pl.pjatk.MATLOG.UserManagement.user.tutor.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.PrivateLessonTutorUserDTO;

@Component
public class TutorUserDTOMapper implements UserDTOMapper {

    private final UserPasswordValidator userPasswordValidator;

    public TutorUserDTOMapper(UserPasswordValidator userPasswordValidator) {
        this.userPasswordValidator = userPasswordValidator;
    }

    @Override
    public TutorUser createUser(UserDTO userDTO) {
        return TutorUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password(), userPasswordValidator)
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }

    public PrivateLessonTutorUserDTO mapToDTO(TutorUser tutorUser) {
        return new PrivateLessonTutorUserDTO(
                tutorUser.getId(),
                tutorUser.getFirstName(),
                tutorUser.getLastName(),
                tutorUser.getEmailAddress()
        );
    }
}
