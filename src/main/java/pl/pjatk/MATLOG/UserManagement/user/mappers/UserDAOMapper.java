package pl.pjatk.MATLOG.UserManagement.user.mappers;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

@Component
public class UserDAOMapper {

    public StudentUser mapToStudentUser(UserDAO user) {
        return mapUserDaoToStudentUser(user);
    }

    public TutorUser mapToTutorUser(UserDAO user) {
        return mapUserDaoToTutorUser(user);
    }

    public UserDAO mapFrom(User user) {
        return mapStudentUserToStudentUserDao(user);
    }

    private StudentUser mapUserDaoToStudentUser(UserDAO userDAO) {
        return StudentUser.builder()
                .withFirstName(userDAO.firstName())
                .withLastName(userDAO.lastName())
                .withEmailAddress(userDAO.emailAddress())
                .withPassword(userDAO.password())
                .withDateOfBirth(userDAO.dateOfBirth())
                .withAuthorities(userDAO.authorities())
                .withIsAccountNonLocked(userDAO.isAccountNonLocked())
                .build();
    }

    private TutorUser mapUserDaoToTutorUser(UserDAO userDAO) {
        return TutorUser.builder()
                .withFirstName(userDAO.firstName())
                .withLastName(userDAO.lastName())
                .withEmailAddress(userDAO.emailAddress())
                .withPassword(userDAO.password())
                .withDateOfBirth(userDAO.dateOfBirth())
                .withAuthorities(userDAO.authorities())
                .withIsAccountNonLocked(userDAO.isAccountNonLocked())
                .build();
    }

    private UserDAO mapStudentUserToStudentUserDao(User user) {
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
