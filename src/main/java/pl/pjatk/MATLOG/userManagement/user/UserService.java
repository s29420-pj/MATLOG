package pl.pjatk.MATLOG.UserManagement.user;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserRepository;

import java.util.Optional;

/**
 * Class that is used to retrieve, modify and delete users from database.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserPasswordValidator userPasswordValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserDAOMapper userMapper;

    public UserService(UserRepository userRepository, UserPasswordValidator userPasswordValidator,
                       PasswordEncoder passwordEncoder, UserDAOMapper userMapper) {
        this.userRepository = userRepository;
        this.userPasswordValidator = userPasswordValidator;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Method that searches for the UserDAO with specified email address (username)
     * and returns it as User
     * @param emailAddress email address of the user
     * @return User
     * @throws UserInvalidEmailAddressException when user with provided email address
     * couldn't be found
     */
    public User findUserByEmailAddress(String emailAddress) throws AuthenticationException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<UserDAO> user = userRepository.findByEmailAddress(emailAddress);
        if (user.isEmpty()) throw new AuthenticationException("User with that email address does not exist.");
        return switch (user.get().role()) {
            case STUDENT -> userMapper.mapToStudentUser(user.get());
            case TUTOR -> userMapper.mapToTutorUser(user.get());
        };
    }

    /**
     * Method that creates new user with provided data. It saves user
     * in database so guest can log in as created account.
     * @param user User that needs to be added to database
     * @throws IllegalArgumentException when user is not provided
     */
    public boolean createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Please provide valid user.");
        }
        Optional<UserDAO> userDao = userRepository.findByEmailAddress(user.getEmailAddress());
        if (userDao.isPresent()) {
            throw new UserAlreadyExistException();
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.changePassword(encodedPassword, userPasswordValidator);
        userRepository.save(userMapper.mapFrom(user));
        return true;
    }
}
