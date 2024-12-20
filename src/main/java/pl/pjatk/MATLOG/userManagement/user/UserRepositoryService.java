package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.UserManagement.user.persistance.TutorUserRepository;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.StudentUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.TutorUserDAOMapper;

import java.util.Optional;

@Service
public class UserRepositoryService {

    private final StudentUserRepository studentUserRepository;
    private final StudentUserDAOMapper studentUserDAOMapper;
    private final TutorUserRepository tutorUserRepository;
    private final TutorUserDAOMapper tutorUserDAOMapper;

    public UserRepositoryService(StudentUserRepository studentUserRepository,
                                 StudentUserDAOMapper studentUserDAOMapper,
                                 TutorUserRepository tutorUserRepository,
                                 TutorUserDAOMapper tutorUserDAOMapper) {
        this.studentUserRepository = studentUserRepository;
        this.studentUserDAOMapper = studentUserDAOMapper;
        this.tutorUserRepository = tutorUserRepository;
        this.tutorUserDAOMapper = tutorUserDAOMapper;
    }

    public User findUserByEmailAddress(String emailAddress) {
        Optional<UserDAO> userFromDatabase = studentUserRepository.findByEmailAddress(emailAddress);
        if (userFromDatabase.isPresent()) {
            return studentUserDAOMapper.createUser(userFromDatabase.get());
        }
        userFromDatabase = tutorUserRepository.findByEmailAddress(emailAddress);
        if (userFromDatabase.isPresent()) {
            return tutorUserDAOMapper.createUser(userFromDatabase.get());
        }
        return null;
    }
}
