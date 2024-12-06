package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;

@Component
public class UserMapper {

    public User mapUserDtoToDomainUser(UserDTO userDTO) {
        return switch (userDTO.role()) {
            case STUDENT -> mapUserDtoToStudentUser(userDTO);
            case TUTOR -> mapUserDtoToTutorUser(userDTO);
        };
    }

    public UserDTO mapDomainUserToUserDto(User user) {
        return new UserDTO(user.getFirstName(), user.getLastName(),
                user.getEmailAddress(), user.getPassword(), user.getDateOfBirth(),
                user.getRole());
    }

    private StudentUser mapUserDtoToStudentUser(UserDTO userDTO) {
        return StudentUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password())
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }

    private TutorUser mapUserDtoToTutorUser(UserDTO userDTO) {
        return TutorUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password())
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }
}
