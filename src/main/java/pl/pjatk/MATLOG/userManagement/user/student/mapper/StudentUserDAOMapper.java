package pl.pjatk.MATLOG.userManagement.user.student.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.privateLessonManagment.PrivateLessonService;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.user.persistance.UserDAO;

/**
 * Mapper that is used to map StudentUser to StudentUserDAO and vice versa.
 */
@Component
public class StudentUserDAOMapper {

    private final PrivateLessonService privateLessonService;
    private final UserPasswordValidator userPasswordValidator;

    public StudentUserDAOMapper(PrivateLessonService privateLessonService, UserPasswordValidator userPasswordValidator) {
        this.privateLessonService = privateLessonService;
        this.userPasswordValidator = userPasswordValidator;
    }

    /**
     * Method that maps User to StudentUserDAO which can be saved in database
     * @param user User representation of StudentUser
     * @return StudentUserDAO
     */
    public StudentUserDAO createUserDAO(User user) {
        return new StudentUserDAO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked());
    }

    /**
     * Method that maps UserDAO to StudentUser which can be used in application.
     * @param user UserDAO which is database representation.
     * @return StudentUser
     */
    public StudentUser createUser(UserDAO user) {
        return StudentUser.builder()
                .withId(user.id())
                .withFirstName(user.firstName())
                .withLastName(user.lastName())
                .withEmailAddress(user.emailAddress())
                .withPassword(user.password(), userPasswordValidator)
                .withDateOfBirth(user.dateOfBirth())
                .withAuthorities(user.authorities())
                .withIsAccountNonLocked(user.isAccountNonLocked())
                .withPrivateLessons(privateLessonService.findByStudentId(user.id()))
                .build();
    }
}
