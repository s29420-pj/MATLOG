package pl.pjatk.MATLOG.UserManagement.studentUser.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

/**
 * Mapper that is used to map StudentUser to StudentUserDAO and vice versa.
 */
@Component
public class StudentUserDAOMapper {

    private final UserPasswordValidator userPasswordValidator;

    public StudentUserDAOMapper(UserPasswordValidator userPasswordValidator) {
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
                .build();
    }
}
