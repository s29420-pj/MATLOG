package pl.pjatk.MATLOG.UserManagement.user.mappers;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

/**
 * Component which is used to get data from the database and map it to the domain User.
 */
@Component
public class UserDAOMapper {

    /**
     * Method that maps UserDAO to the StudentUser
     * @param user UserDAO from the database
     * @return StudentUser
     */
    public StudentUser mapToStudentUser(UserDAO user) {
        return StudentUser.builder()
                .withFirstName(user.firstName())
                .withLastName(user.lastName())
                .withEmailAddress(user.emailAddress())
                .withPassword(user.password())
                .withDateOfBirth(user.dateOfBirth())
                .withAuthorities(user.authorities())
                .withIsAccountNonLocked(user.isAccountNonLocked())
                .build();
    }

    /**
     * Method that maps UserDAO to the TutorUser
     * @param user UserDAO from the database
     * @return TutorUser
     */
    public TutorUser mapToTutorUser(UserDAO user) {
        return TutorUser.builder()
                .withFirstName(user.firstName())
                .withLastName(user.lastName())
                .withEmailAddress(user.emailAddress())
                .withPassword(user.password())
                .withDateOfBirth(user.dateOfBirth())
                .withAuthorities(user.authorities())
                .withIsAccountNonLocked(user.isAccountNonLocked())
                .build();
    }

    /**
     * Method that maps domain User to the UserDAO
     * @param user domain User from which will be created UserDAO
     * @return UserDAO
     */
    public UserDAO mapFrom(User user) {
        return new UserDAO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked(),
                user.getRole());
    }
}
