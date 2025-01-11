package pl.pjatk.MATLOG.userManagement.studentUser;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
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

    public void changePassword(String id, String rawPassword) {
        StudentUser studentUser = getStudentUserById(id);
        studentUser.changePassword(passwordEncoder.encode(rawPassword), userPasswordValidator);
        studentUserRepository.save(studentUserDAOMapper.mapToDAO(studentUser));
    }

    private boolean checkIfStudentUserExists(String id) {
        Optional<StudentUserDAO> student = studentUserRepository.findById(id);
        return student.isPresent();
    }

    public StudentUser getStudentUserById(String id) {
        Optional<StudentUserDAO> studentUserDAO = studentUserRepository.findById(id);
        if (studentUserDAO.isEmpty()) throw new UserNotFoundException();
        return studentUserDAOMapper.mapToDomain(studentUserDAO.get());
    }

    public StudentUserDAO getStudentUserDAOById(String id) {
        return studentUserRepository.findById(id).get();
    }
}
