package pl.pjatk.MATLOG.userManagement.studentUser;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Exceptions.UserExceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.userManagement.user.UserRepositoryService;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.Optional;

@Service
public class StudentUserService {

    private final StudentUserRepository studentUserRepository;
    private final UserRepositoryService userRepositoryService;
    private final UserPasswordValidator userPasswordValidator;
    private final StudentUserDAOMapper studentUserDAOMapper;
    private final StudentUserDTOMapper studentUserDTOMapper;
    private final StudentUserReviewDTOMapper studentUserReviewDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public StudentUserService(StudentUserRepository studentUserRepository,
                              UserRepositoryService userRepositoryService,
                              UserPasswordValidator userPasswordValidator, StudentUserDAOMapper studentUserDAOMapper, StudentUserDTOMapper studentUserDTOMapper, StudentUserReviewDTOMapper studentUserReviewDTOMapper,
                              PasswordEncoder passwordEncoder) {
        this.studentUserRepository = studentUserRepository;
        this.userRepositoryService = userRepositoryService;
        this.userPasswordValidator = userPasswordValidator;
        this.studentUserDAOMapper = studentUserDAOMapper;
        this.studentUserDTOMapper = studentUserDTOMapper;
        this.studentUserReviewDTOMapper = studentUserReviewDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public StudentUser findUserByEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<StudentUserDAO> studentUserDAO = studentUserRepository.findByEmailAddress(emailAddress);
        if (studentUserDAO.isEmpty()) {
            throw new UsernameNotFoundException("Student user not found");
        }
        return studentUserDAOMapper.mapToDomain(studentUserDAO.get());
    }

    public void registerUser(UserRegistrationDTO userDTO) throws IllegalArgumentException, UserAlreadyExistsException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }
        User user = userRepositoryService.findUserByEmailAddress(userDTO.emailAddress());
        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        StudentUser domainUser = studentUserDTOMapper.mapToDomain(userDTO);

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), userPasswordValidator);

        StudentUserDAO student = studentUserDAOMapper.mapToDAO(domainUser);

        studentUserRepository.save(student);
    }
}
