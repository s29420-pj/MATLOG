package pl.pjatk.MATLOG.UserManagement.user.persistance.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.PrivateLessonManagment.PrivateLessonService;
import pl.pjatk.MATLOG.UserManagement.user.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;

@Component
public class TutorUserDAOMapper {

    private final PrivateLessonService privateLessonService;
    private final ReviewService reviewService;

    public TutorUserDAOMapper(PrivateLessonService privateLessonService, ReviewService reviewService) {
        this.privateLessonService = privateLessonService;
        this.reviewService = reviewService;
    }

    public TutorUserDAO createTutorUserDAO(TutorUser user) {
        return new TutorUserDAO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked());
    }

    public TutorUser createTutorUser(TutorUserDAO userDAO) {
        return TutorUser.builder()
                .withId(userDAO.id())
                .withFirstName(userDAO.firstName())
                .withLastName(userDAO.lastName())
                .withEmailAddress(userDAO.emailAddress())
                .withPassword(userDAO.password())
                .withDateOfBirth(userDAO.dateOfBirth())
                .withAuthorities(userDAO.authorities())
                .withIsAccountNonLocked(userDAO.isAccountNonLocked())
                .withPrivateLessons(privateLessonService.findByTutorId(userDAO.id()))
                .withReviews(reviewService.getTutorUserReviews(userDAO.id()))
                .build();
    }
}
