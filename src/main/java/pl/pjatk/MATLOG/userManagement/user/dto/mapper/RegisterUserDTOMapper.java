package pl.pjatk.MATLOG.UserManagement.user.dto.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserRegistrationDTO;

/**
 * Component which is used to receive data from registration form and map it to the domain User.
 */
@Component
public class RegisterUserDTOMapper {

    /**
     * Method that maps UserRegistrationDTO to domain User. Maps to the exact user by role.
     * It can create StudentUser or TutorUser respectively.
     * @param userRegistrationDTO user to register
     * @return User based on Role property
     */
    public User mapTo(UserRegistrationDTO userRegistrationDTO) {
        return switch (userRegistrationDTO.role()) {
            case STUDENT -> mapUserDtoToStudentUser(userRegistrationDTO);
            case TUTOR -> mapUserDtoToTutorUser(userRegistrationDTO);
        };
    }

    /**
     * Method that maps UserRegistrationDTO to StudentUser
     * @param userRegistrationDTO DTO which is used for registration purpose
     * @return StudentUser
     */
    private StudentUser mapUserDtoToStudentUser(UserRegistrationDTO userRegistrationDTO) {
        return StudentUser.builder()
                .withFirstName(userRegistrationDTO.firstName())
                .withLastName(userRegistrationDTO.lastName())
                .withEmailAddress(userRegistrationDTO.emailAddress())
                .withPassword(userRegistrationDTO.password())
                .withDateOfBirth(userRegistrationDTO.dateOfBirth())
                .build();
    }

    /**
     * Method that maps UserRegistrationDTO to TutorUser
     * @param userRegistrationDTO DTO which is used for registration purpose
     * @return TutorUser
     */
    private TutorUser mapUserDtoToTutorUser(UserRegistrationDTO userRegistrationDTO) {
        return TutorUser.builder()
                .withFirstName(userRegistrationDTO.firstName())
                .withLastName(userRegistrationDTO.lastName())
                .withEmailAddress(userRegistrationDTO.emailAddress())
                .withPassword(userRegistrationDTO.password())
                .withDateOfBirth(userRegistrationDTO.dateOfBirth())
                .build();
    }
}
