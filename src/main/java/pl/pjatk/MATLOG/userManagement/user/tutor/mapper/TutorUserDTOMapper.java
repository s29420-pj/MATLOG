package pl.pjatk.MATLOG.userManagement.user.tutor.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserDTOMapper;

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
}
