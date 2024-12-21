package pl.pjatk.MATLOG.UserManagement.user;

import org.apache.tomcat.websocket.AuthenticationException;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserAlreadyExistException;
import pl.pjatk.MATLOG.UserManagement.Exceptions.UserInvalidEmailAddressException;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;

public interface UserService {

    User findUserByEmailAddress(String emailAddress) throws AuthenticationException, UserInvalidEmailAddressException;
    void registerUser(UserDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException;
}
