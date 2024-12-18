package pl.pjatk.MATLOG.UserManagement.user;

import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;

public interface UserMapperFactory {

    UserDTO createDTO();
    UserDAO createDAO(User user);
}
