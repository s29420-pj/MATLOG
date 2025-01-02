package pl.pjatk.MATLOG.UserManagement.user.student;

import pl.pjatk.MATLOG.Domain.StudentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.UserRepositoryService;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserRegistrationDTO;

import java.util.Optional;

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

    public StudentUser findUserByEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<StudentUserDAO> studentUserDAO = studentUserRepository.findByEmailAddress(emailAddress);
        if (studentUserDAO.isEmpty()) {
            throw new UserNotFoundException();
        }
        return studentUserMapperFactory.getUserDAOMapper().createUser(studentUserDAO.get());
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
