package pl.pjatk.MATLOG.userManagement.studentUser;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserChangePasswordDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.userManagement.user.UserRepositoryService;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.Optional;

@Service
@Transactional
public class StudentUserService {

    private final StudentUserRepository studentUserRepository;
    private final UserRepositoryService userRepositoryService;
    private final UserPasswordValidator userPasswordValidator;
    private final StudentUserDAOMapper studentUserDAOMapper;
    private final StudentUserDTOMapper studentUserDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public StudentUserService(StudentUserRepository studentUserRepository,
                              UserRepositoryService userRepositoryService,
                              UserPasswordValidator userPasswordValidator,
                              StudentUserDAOMapper studentUserDAOMapper,
                              StudentUserDTOMapper studentUserDTOMapper,
                              PasswordEncoder passwordEncoder) {
        this.studentUserRepository = studentUserRepository;
        this.userRepositoryService = userRepositoryService;
        this.userPasswordValidator = userPasswordValidator;
        this.studentUserDAOMapper = studentUserDAOMapper;
        this.studentUserDTOMapper = studentUserDTOMapper;
        this.passwordEncoder = passwordEncoder;
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

        domainUser.unblock();

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), userPasswordValidator);

        StudentUserDAO student = studentUserDAOMapper.mapToDAO(domainUser);

        studentUserRepository.save(student);
    }

    public StudentUserProfileDTO getStudentProfile(String id) {
        StudentUser studentUser = getStudentUserById(id);
        return studentUserDTOMapper.mapToDTO(studentUser);
    }

    public StudentUserProfileDTO getStudentProfileByEmailAddress(String emailAddress) {
        return studentUserDTOMapper.mapToDTO(getStudentUserByEmailAdress(emailAddress));
    }

    public void changePassword(StudentUserChangePasswordDTO studentUserChangePasswordDTO) {
        StudentUser studentUser = getStudentUserById(studentUserChangePasswordDTO.id());
        studentUser.changePassword(passwordEncoder.encode(studentUserChangePasswordDTO.rawPassword()), userPasswordValidator);
        studentUserRepository.save(studentUserDAOMapper.mapToDAO(studentUser));
    }

    private StudentUser getStudentUserByEmailAdress(String emailAddress) {
        Optional<StudentUserDAO> studentUserDAO = studentUserRepository.findByEmailAddress(emailAddress);
        if (studentUserDAO.isEmpty()) throw new UserNotFoundException();
        return studentUserDAOMapper.mapToDomain(studentUserDAO.get());
    }

    public StudentUser getStudentUserById(String id) {
        Optional<StudentUserDAO> studentUserDAO = studentUserRepository.findById(id);
        if (studentUserDAO.isEmpty()) throw new UserNotFoundException();
        return studentUserDAOMapper.mapToDomain(studentUserDAO.get());
    }
}
