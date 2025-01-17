package pl.pjatk.MATLOG.userManagement.tutorUser.persistance;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAOMapper;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;

import java.util.stream.Collectors;

/**
 * Mapper that is used to map TutorUser to TutorUserDAO and vice versa.
 */
@Component
public class TutorUserDAOMapper {

    private final UserPasswordValidator userPasswordValidator;
    private final ReviewDAOMapper reviewDAOMapper;

    public TutorUserDAOMapper(UserPasswordValidator userPasswordValidator,
                              ReviewDAOMapper reviewDAOMapper) {
        this.userPasswordValidator = userPasswordValidator;
        this.reviewDAOMapper = reviewDAOMapper;
    }

    /**
     * Method that maps User to TutorUserDAO which can be saved in database
     * @param user User representation of TutorUser
     * @return TutorUserDAO
     */
    public TutorUserDAO mapToDAO(TutorUser user) {
        return new TutorUserDAO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked(),
                user.getBiography(),
                user.getSpecializations(),
                user.getReviews().stream()
                        .map(reviewDAOMapper::mapToDAO)
                        .collect(Collectors.toSet())
        );
    }

    /**
     * Method that maps UserDAO to TutorUser which can be used in application.
     * @param user UserDAO which is database representation.
     * @return TutorUser
     */
    public TutorUser mapToDomain(TutorUserDAO user) {
        return TutorUser.builder()
                .withId(user.id)
                .withFirstName(user.firstName)
                .withLastName(user.lastName)
                .withEmailAddress(user.emailAddress)
                .withPassword(user.password, userPasswordValidator)
                .withDateOfBirth(user.dateOfBirth)
                .withBiography(user.biography)
                .withSpecializations(user.specializations)
                .withAuthorities(user.authorities)
                .withIsAccountNonLocked(user.isAccountNonLocked)
                .withReviews(user.reviews.stream()
                        .map(reviewDAOMapper::mapToDomain)
                        .collect(Collectors.toSet()))
                .build();
    }
}
