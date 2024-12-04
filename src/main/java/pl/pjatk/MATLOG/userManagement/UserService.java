package pl.pjatk.MATLOG.userManagement;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.exceptions.UserUnsecurePasswordException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;

import java.util.Optional;

/**
 * Class that is used to retrieve, modify and delete users from database.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserPasswordValidator userPasswordValidator;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserPasswordValidator userPasswordValidator,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userPasswordValidator = userPasswordValidator;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method that searches for the user with specified email address (username)
     * @param emailAddress email address of the user
     * @return User
     * @throws UserInvalidEmailAddressException when user with provided email address
     * couldn't be found
     */
    public User findUserByEmailAddress(String emailAddress) throws AuthenticationException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        if (user.isEmpty()) throw new AuthenticationException("User with that email address does not exist.");
        return user.get();
    }

    /**
     * Method that creates new user with provided data. It saves user
     * in database so guest can log in as created account.
     * @param user User that needs to be added to database
     * @throws IllegalArgumentException when user is not provided
     */
    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Please provide valid user.");
        }
        if (!userPasswordValidator.isSecure(user.getPassword())) throw new UserUnsecurePasswordException();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.changePassword(encodedPassword);
        userRepository.save(user);
    }
}
