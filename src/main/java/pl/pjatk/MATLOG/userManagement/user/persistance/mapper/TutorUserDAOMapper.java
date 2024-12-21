package pl.pjatk.MATLOG.UserManagement.user.persistance.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.PrivateLessonManagment.PrivateLessonService;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;

@Component
public class TutorUserDAOMapper implements UserDAOMapper {

    private final PrivateLessonService privateLessonService;
    private final ReviewService reviewService;
    private final UserPasswordValidator userPasswordValidator;


    public TutorUserDAOMapper(PrivateLessonService privateLessonService, ReviewService reviewService, UserPasswordValidator userPasswordValidator) {
        this.privateLessonService = privateLessonService;
        this.reviewService = reviewService;
        this.userPasswordValidator = userPasswordValidator;
    }

    @Override
    public TutorUserDAO createUserDAO(User user) {
        return new TutorUserDAO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked());
    }

    @Override
    public TutorUser createUser(UserDAO user) {
        return TutorUser.builder()
                .withId(user.id())
                .withFirstName(user.firstName())
                .withLastName(user.lastName())
                .withEmailAddress(user.emailAddress())
                .withPassword(user.password(), userPasswordValidator)
                .withDateOfBirth(user.dateOfBirth())
                .withAuthorities(user.authorities())
                .withIsAccountNonLocked(user.isAccountNonLocked())
                .withPrivateLessons(privateLessonService.findByTutorId(user.id()))
                .withReviews(reviewService.getTutorUserReviews(user.id()))
                .build();

    }
}
