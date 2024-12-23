package pl.pjatk.MATLOG.userManagement.user.tutor;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;
import pl.pjatk.MATLOG.userManagement.user.tutor.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.userManagement.user.tutor.persistance.TutorUserRepository;

import java.util.Optional;

/**
 * TutorUserService class which talks to repository layer to get data from database.
 * It's used by Controller related to tutor operations.
 */
@Service
public class TutorUserService {

    private final TutorUserRepository tutorUserRepository;
    private final TutorUserMapperFactory tutorUserMapperFactory;
    private final PasswordEncoder passwordEncoder;
    private final UserPasswordValidator passwordValidator;

    public TutorUserService(TutorUserRepository tutorUserRepository,
                            TutorUserMapperFactory tutorUserMapperFactory,
                            PasswordEncoder passwordEncoder,
                            UserPasswordValidator passwordValidator) {
        this.tutorUserRepository = tutorUserRepository;
        this.tutorUserMapperFactory = tutorUserMapperFactory;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
    }

    /**
     *
     * @param emailAddress Email address of the tutor user
     * @return TutorUser
     * @throws AuthenticationException if tutor with provided email address has not been found
     * @throws UserInvalidEmailAddressException if provided email address is null or empty
     */
    public TutorUser findUserByEmailAddress(String emailAddress) throws AuthenticationException, UserInvalidEmailAddressException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<TutorUserDAO> userFromDatabase = tutorUserRepository.findByEmailAddress(emailAddress);
        if (userFromDatabase.isEmpty()) {
            throw new AuthenticationException("User with that email address does not exist.");
        }
        return tutorUserMapperFactory.getUserDAOMapper()
                .createUser(userFromDatabase.get());
    }

    /**
     * Method that is used to register valid tutor user.
     * @param userDTO DTO representation of the user
     * @throws IllegalArgumentException If DTO is null
     * @throws UserAlreadyExistException if user with provided email address exists.
     */
    public void registerUser(UserRegistrationDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }

        if (checkIfTutorExists(userDTO.emailAddress())) {
            throw new UserAlreadyExistException();
        }

        TutorUser domainUser = tutorUserMapperFactory
                .getUserDTOMapper()
                .createUser(userDTO);

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), passwordValidator);

        TutorUserDAO tutor = tutorUserMapperFactory
                .getUserDAOMapper()
                .createUserDAO(domainUser);

        tutorUserRepository.save(tutor);
    }

    /**
     * Method which is used to change tutor's user password.
     * @param tutorUser Tutor user which will have password changed.
     * @param rawPassword new password
     */
    public void changePassword(TutorUser tutorUser, String rawPassword) {
        tutorUser.changePassword(passwordEncoder.encode(rawPassword), passwordValidator);
        tutorUserRepository.save(tutorUserMapperFactory.getUserDAOMapper().createUserDAO(tutorUser));
    }

    /**
     * Method that checks if user with provided email address exists.
     * @param emailAddress email address of user
     * @return boolean - true if exists, false otherwise.
     */
    private boolean checkIfTutorExists(String emailAddress) {
        Optional<TutorUserDAO> tutor = tutorUserRepository.findByEmailAddress(emailAddress);
        return tutor.isPresent();
    }
}
