package pl.pjatk.MATLOG.userManagement.studentUser.mapper;

import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.LoggedUserDTO;
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

    public StudentUserProfileDTO mapToDTO(StudentUser studentUser) {
        return new StudentUserProfileDTO(
            studentUser.getId(),
            studentUser.getFirstName(),
            studentUser.getLastName(),
            studentUser.getDateOfBirth()
        );
    }

    public LoggedUserDTO mapToLogin(StudentUser studentUser) {
        return LoggedUserDTO.builder()
                .id(studentUser.getId())
                .username(studentUser.getEmailAddress())
                .firstName(studentUser.getFirstName())
                .lastName(studentUser.getLastName())
                .token("")
                .roles(studentUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();
    }
}
