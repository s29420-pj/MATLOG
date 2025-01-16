package pl.pjatk.MATLOG.userManagement.tutorUser.mapper;

import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.stream.Collectors;

@Mapper
public class TutorUserDTOMapper {

    private final UserPasswordValidator userPasswordValidator;
    private final ReviewDTOMapper reviewDTOMapper;

    public TutorUserDTOMapper(UserPasswordValidator userPasswordValidator,
                              ReviewDTOMapper reviewDTOMapper) {
        this.userPasswordValidator = userPasswordValidator;
        this.reviewDTOMapper = reviewDTOMapper;
    }

    public TutorUser mapToDomain(UserRegistrationDTO userDTO) {
        return TutorUser.builder()
                .withFirstName(userDTO.firstName())
                .withLastName(userDTO.lastName())
                .withEmailAddress(userDTO.emailAddress())
                .withPassword(userDTO.password(), userPasswordValidator)
                .withDateOfBirth(userDTO.dateOfBirth())
                .build();
    }

    public TutorUserProfileDTO mapToProfile(TutorUser tutorUser) {
        return new TutorUserProfileDTO(
                tutorUser.getId(),
                tutorUser.getFirstName(),
                tutorUser.getLastName(),
                tutorUser.getBiography(),
                tutorUser.getSpecializations(),
                tutorUser.getReviews().stream()
                        .map(reviewDTOMapper::mapToDTO)
                        .collect(Collectors.toSet())
        );
    }
}
