package pl.pjatk.MATLOG.UserManagement.securityConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.SecurityUser;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserRepository;

import java.util.Optional;

/**
 * Component that loads user from the database
 */
@Component
public class MongoUserDetailsService implements UserDetailsService {

    private final StudentUserRepository studentUserRepository;
    private final UserDAOMapper userDAOMapper;

    public MongoUserDetailsService(StudentUserRepository studentUserRepository, UserDAOMapper userDAOMapper) {
        this.studentUserRepository = studentUserRepository;
        this.userDAOMapper = userDAOMapper;
    }

    /**
     * Method that loads user by username (email address) from database.
     * @param username the username (email address) identifying the user whose data is required.
     * @return SecurityUser which represents User in application
     */
    @Override
    public SecurityUser loadUserByUsername(String username) {
        Optional<UserDAO> userDAO = studentUserRepository.findByEmailAddress(username);
        if (userDAO.isEmpty()) {
            throw new UsernameNotFoundException("User with that email address does not exist.");
        }
        return new SecurityUser(returnUserFromUserDAO(userDAO.get()));
    }

    /**
     * Determines if user from database is STUDENT or TUTOR
     * @param userDAO user from database
     * @return User
     */
    private User returnUserFromUserDAO(UserDAO userDAO) {
        return userDAO.role() == Role.STUDENT ?
                userDAOMapper.mapToStudentUser(userDAO) : userDAOMapper.mapToTutorUser(userDAO);
    }
}
