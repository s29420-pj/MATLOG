package pl.pjatk.MATLOG.UserManagement.securityConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.SecurityUser;
import pl.pjatk.MATLOG.UserManagement.user.mappers.UserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserRepository;
import pl.pjatk.MATLOG.UserManagement.user.UserService;

import java.util.Optional;
import java.util.function.Supplier;

@Component
public class MongoUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserDAOMapper userDAOMapper;

    public MongoUserDetailsService(UserService userService, UserRepository userRepository, UserDAOMapper userDAOMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDAOMapper = userDAOMapper;
    }

    @Override
    public SecurityUser loadUserByUsername(String username) {
        Optional<UserDAO> userDAO = userRepository.findByEmailAddress(username);
        if (userDAO.isEmpty()) {
            throw new UsernameNotFoundException("User with that email address does not exist.");
        }
        return new SecurityUser(returnUserFromUserDAO(userDAO.get()));
    }

    private User returnUserFromUserDAO(UserDAO userDAO) {
        return userDAO.role() == Role.STUDENT ?
                userDAOMapper.mapToStudentUser(userDAO) : userDAOMapper.mapToTutorUser(userDAO);
    }
}
