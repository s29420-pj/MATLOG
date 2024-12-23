package pl.pjatk.MATLOG.userManagement.user.student.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserDTOMapper;

@Component
public class StudentUserDTOMapper implements UserDTOMapper {

    private UserPasswordValidator userPasswordValidator;

    public StudentUserDTOMapper(UserPasswordValidator userPasswordValidator) {
        this.userPasswordValidator = userPasswordValidator;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        return StudentUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password(), userPasswordValidator)
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }
}
