package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserRepository;

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
    public User finqdUserByEmailAddress(String emailAddress) {
        Optional<StudentUserDAO> studentFromDatabase = studentUserRepository.findByEmailAddress(emailAddress);
        if (studentFromDatabase.isPresent()) {
            return studentUserDAOMapper.createUser(studentFromDatabase.get());
        }
        Optional<TutorUserDAO> tutorFromDatabase = tutorUserRepository.findByEmailAddress(emailAddress);
        if (tutorFromDatabase.isPresent()) {
            return tutorUserDAOMapper.createUser(tutorFromDatabase.get());
        }
        return null;
    }
}
