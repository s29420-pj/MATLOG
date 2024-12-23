package pl.pjatk.MATLOG.userManagement.user.student;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.user.UserRepositoryService;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;
import pl.pjatk.MATLOG.userManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.user.student.persistance.StudentUserRepository;

@Service
public class StudentUserService {

    private final StudentUserRepository studentUserRepository;
    private final UserRepositoryService userRepositoryService;
    private final StudentUserMapperFactory studentUserMapperFactory;
    private final UserPasswordValidator userPasswordValidator;
    private final PasswordEncoder passwordEncoder;

    public StudentUserService(StudentUserRepository studentUserRepository,
                              UserRepositoryService userRepositoryService,
                              StudentUserMapperFactory studentUserMapperFactory,
                              UserPasswordValidator userPasswordValidator,
                              PasswordEncoder passwordEncoder) {
        this.studentUserRepository = studentUserRepository;
        this.userRepositoryService = userRepositoryService;
        this.studentUserMapperFactory = studentUserMapperFactory;
        this.userPasswordValidator = userPasswordValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByEmailAddress(String emailAddress) throws AuthenticationException, UserInvalidEmailAddressException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        User user = userRepositoryService.findUserByEmailAddress(emailAddress);
        if (user == null) {
            throw new AuthenticationException("User with that email address does not exist.");
        }
        return user;
    }

    public void registerUser(UserRegistrationDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }
        User user = userRepositoryService.findUserByEmailAddress(userDTO.emailAddress());
        if (user != null) {
            throw new UserAlreadyExistException();
        }

        User domainUser = studentUserMapperFactory
                .getUserDTOMapper().createUser(userDTO);

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), userPasswordValidator);

        StudentUserDAO student = studentUserMapperFactory
                .getUserDAOMapper()
                .createUserDAO(domainUser);

        studentUserRepository.save(student);
    }
}
