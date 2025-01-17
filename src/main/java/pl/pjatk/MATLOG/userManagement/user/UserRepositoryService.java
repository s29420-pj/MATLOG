package pl.pjatk.MATLOG.userManagement.user;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserRepository;

import java.util.Optional;

/**
 * Class that wraps all repositories to query all tables containing users.
 */
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

    /**
     * Method that searches through students table and tutors
     * @param emailAddress email address of the user
     * @return User
     */
    public User findUserByEmailAddress(String emailAddress) {
        Optional<StudentUserDAO> studentFromDatabase = studentUserRepository.findByEmailAddress(emailAddress);
        if (studentFromDatabase.isPresent()) {
            return studentUserDAOMapper.mapToDomain(studentFromDatabase.get());
        }
        Optional<TutorUserDAO> tutorFromDatabase = tutorUserRepository.findByEmailAddress(emailAddress);
        if (tutorFromDatabase.isPresent()) {
            return tutorUserDAOMapper.mapToDomain(tutorFromDatabase.get());
        }
        return null;
    }
}
