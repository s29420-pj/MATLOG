package pl.pjatk.MATLOG.UserManagement.user.dto.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;

@Component
public class RegisterStudentUserDTOMapper {

    public StudentUser createStudentUser(UserDTO userDTO) {
        return StudentUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password())
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }
}
