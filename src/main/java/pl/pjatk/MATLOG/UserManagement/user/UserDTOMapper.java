package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;

@Component
public class UserDTOMapper {

    public User mapTo(UserRegisterDTO userRegisterDTO) {
        return switch (userRegisterDTO.role()) {
            case STUDENT -> mapUserDtoToStudentUser(userRegisterDTO);
            case TUTOR -> mapUserDtoToTutorUser(userRegisterDTO);
        };
    }

    public UserRegisterDTO mapFrom(User user) {
        return new UserRegisterDTO(user.getFirstName(), user.getLastName(),
                user.getEmailAddress(), user.getPassword(), user.getDateOfBirth(),
                user.getRole());
    }

    private StudentUser mapUserDtoToStudentUser(UserRegisterDTO userRegisterDTO) {
        return StudentUser.builder()
                .withFirstName(userRegisterDTO.firstName())
                .withLastName(userRegisterDTO.lastName())
                .withEmailAddress(userRegisterDTO.emailAddress())
                .withPassword(userRegisterDTO.password())
                .withDateOfBirth(userRegisterDTO.dateOfBirth())
                .build();
    }

    private TutorUser mapUserDtoToTutorUser(UserRegisterDTO userRegisterDTO) {
        return TutorUser.builder()
                .withFirstName(userRegisterDTO.firstName())
                .withLastName(userRegisterDTO.lastName())
                .withEmailAddress(userRegisterDTO.emailAddress())
                .withPassword(userRegisterDTO.password())
                .withDateOfBirth(userRegisterDTO.dateOfBirth())
                .build();
    }
}
