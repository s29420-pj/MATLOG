package pl.pjatk.MATLOG.UserManagement.user;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

import java.util.Optional;

@Service
public class StudentUserService implements UserService {

    private final StudentUserRepository studentUserRepository;
    private final StudentUserMapperFactory studentUserMapperFactory;

    public StudentUserService(StudentUserRepository studentUserRepository,
                              StudentUserMapperFactory userMapperFactory) {
        this.studentUserRepository = studentUserRepository;
        this.studentUserMapperFactory = userMapperFactory;
    }

    @Override
    public User findUserByEmailAddress(String emailAddress) throws AuthenticationException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<UserDAO> user = studentUserRepository.findByEmailAddress(emailAddress);
        if (user.isEmpty()) {
            throw new AuthenticationException("User with that email address does not exists.");
        }
        return studentUserMapperFactory
                .getUserDAOMapper()
                .createUser(user.get());
    }

    @Override
    public void registerUser(UserDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }
        Optional<UserDAO> user = studentUserRepository.findByEmailAddress(userDTO.emailAddress());
        if (user.isEmpty()) {
            throw new UserAlreadyExistException();
        }
        User domainUser = studentUserMapperFactory.getUserDTOMapper().createUser(userDTO);
        StudentUserDAO student = studentUserMapperFactory.getUserDAOMapper().createUserDAO(domainUser);
        studentUserRepository.save(student);
    }
}
