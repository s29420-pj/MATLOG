package pl.pjatk.MATLOG.userManagement.studentUser.mapper;

import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

@Mapper
public class StudentUserDTOMapper {

    private UserPasswordValidator userPasswordValidator;

    public StudentUserDTOMapper(UserPasswordValidator userPasswordValidator) {
        this.userPasswordValidator = userPasswordValidator;
    }

    public StudentUser mapToDomain(UserRegistrationDTO userDTO) {
        return StudentUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password(), userPasswordValidator)
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }
}
