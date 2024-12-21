package pl.pjatk.MATLOG.UserManagement.user;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.TutorUserRepository;

@Service
public class TutorUserService implements UserService{

    private final TutorUserRepository tutorUserRepository;
    private final UserRepositoryService userRepositoryService;
    private final TutorUserMapperFactory tutorUserMapperFactory;
    private final PasswordEncoder passwordEncoder;
    private final UserPasswordValidator passwordValidator;

    public TutorUserService(TutorUserRepository tutorUserRepository,
                            UserRepositoryService userRepositoryService,
                            TutorUserMapperFactory tutorUserMapperFactory, PasswordEncoder passwordEncoder, UserPasswordValidator passwordValidator) {
        this.tutorUserRepository = tutorUserRepository;
        this.userRepositoryService = userRepositoryService;
        this.tutorUserMapperFactory = tutorUserMapperFactory;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public User findUserByEmailAddress(String emailAddress) throws AuthenticationException, UserInvalidEmailAddressException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        User userFromDatabase = userRepositoryService.findUserByEmailAddress(emailAddress);
        if (userFromDatabase == null) {
            throw new AuthenticationException("User with that email address does not exist.");
        }
        return userFromDatabase;
    }

    @Override
    public void registerUser(UserDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }
        User user = userRepositoryService.findUserByEmailAddress(userDTO.emailAddress());
        if (user != null) {
            throw new UserAlreadyExistException();
        }
        User domainUser = tutorUserMapperFactory
                .getUserDTOMapper()
                .createUser(userDTO);

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), passwordValidator);

        TutorUserDAO tutor = tutorUserMapperFactory
                .getUserDAOMapper()
                .createUserDAO(domainUser);

        tutorUserRepository.save(tutor);
    }

    public void changePassword(TutorUser tutorUser, String rawPassword) {
        tutorUser.changePassword(rawPassword, passwordValidator);
        tutorUserRepository.save(tutorUserMapperFactory.getUserDAOMapper().createUserDAO(tutorUser));
    }
}
