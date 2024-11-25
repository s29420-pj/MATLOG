package pl.pjatk.MATLOG.userManagement;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method that searches for the user with specified email address (username)
     * @param emailAddress email address of the user
     * @return User
     * @throws UserInvalidEmailAddressException when user with provided email address
     * couldn't be found
     */
    public User findUserByEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        if (user.isEmpty()) throw new UserNotFoundException();
        return user.get();
    }
}
