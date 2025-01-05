package pl.pjatk.MATLOG.UserManagement.tutorUser.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.AvailablePrivateLessonDTO;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.PrivateLessonTutorUserDTO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;

import java.util.List;

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

    public PrivateLessonTutorUserDTO mapToPrivateLessonTutorUserDTO(TutorUser tutorUser) {
        return new PrivateLessonTutorUserDTO(
                tutorUser.getId(),
                tutorUser.getFirstName(),
                tutorUser.getLastName(),
                tutorUser.getEmailAddress()
        );
    }


    public TutorUserProfileDTO createUserProfile(TutorUser tutor, List<ReviewLookUpDTO> reviews,
                                                 List<AvailablePrivateLessonDTO> availableLessons) {
        return new TutorUserProfileDTO(
                tutor.getId(),
                tutor.getFirstName(),
                tutor.getLastName(),
                tutor.getDateOfBirth(),
                tutor.getBiography(),
                tutor.getSpecializations(),
                reviews,
                availableLessons
                );
    }
}
