package pl.pjatk.MATLOG.UserManagement.user.persistance.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.PrivateLessonManagment.PrivateLessonService;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;

/**
 * Mapper that is used to map TutorUser to TutorUserDAO and vice versa.
 */
@Component
public class TutorUserDAOMapper {

    private final PrivateLessonService privateLessonService;
    private final ReviewService reviewService;
    private final UserPasswordValidator userPasswordValidator;


    public TutorUserDAOMapper(PrivateLessonService privateLessonService, ReviewService reviewService, UserPasswordValidator userPasswordValidator) {
        this.privateLessonService = privateLessonService;
        this.reviewService = reviewService;
        this.userPasswordValidator = userPasswordValidator;
    }

    /**
     * Method that maps User to TutorUserDAO which can be saved in database
     * @param user User representation of TutorUser
     * @return TutorUserDAO
     */
    public TutorUserDAO createUserDAO(TutorUser user) {
        return new TutorUserDAO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getBiography(),
                user.getSpecializations(),
                user.getAuthorities(),
                user.isAccountNonLocked());
    }

    /**
     * Method that maps UserDAO to TutorUser which can be used in application.
     * @param user UserDAO which is database representation.
     * @return TutorUser
     */
    public TutorUser createUser(TutorUserDAO user) {
        return TutorUser.builder()
                .withId(user.id())
                .withFirstName(user.firstName())
                .withLastName(user.lastName())
                .withEmailAddress(user.emailAddress())
                .withPassword(user.password(), userPasswordValidator)
                .withDateOfBirth(user.dateOfBirth())
                .withBiography(user.biography())
                .withSpecializations(user.specializations())
                .withAuthorities(user.authorities())
                .withIsAccountNonLocked(user.isAccountNonLocked())
                .withPrivateLessons(privateLessonService.findByTutorId(user.id()))
                .withReviews(reviewService.getTutorUserReviews(user.id()))
                .build();

    }
}
