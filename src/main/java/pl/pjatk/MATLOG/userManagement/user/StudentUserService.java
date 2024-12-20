package pl.pjatk.MATLOG.UserManagement.user;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserRepository;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

import java.util.Optional;

@Service
public class StudentUserService implements UserService {

    private final StudentUserRepository studentUserRepository;
    private final UserMapperFactory userMapperFactory;

    public StudentUserService(StudentUserRepository studentUserRepository,
                              @Qualifier("studentUserMapperFactory") UserMapperFactory userMapperFactory) {
        this.studentUserRepository = studentUserRepository;
        this.userMapperFactory = userMapperFactory;
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
        return userMapperFactory
                .getUserDAOMapper()
                .createUser(user.get());
    }

    @Override
    public boolean registerUser(UserDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException {
        return false;
    }
}
