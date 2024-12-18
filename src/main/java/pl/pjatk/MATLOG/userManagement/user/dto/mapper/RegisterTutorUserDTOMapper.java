package pl.pjatk.MATLOG.UserManagement.user.dto.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;

@Component
public class RegisterTutorUserDTOMapper {

    public TutorUser createTutorUser(UserDTO userDTO) {
        return TutorUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password())
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }
}
