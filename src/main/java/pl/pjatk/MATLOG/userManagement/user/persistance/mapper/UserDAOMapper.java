package pl.pjatk.MATLOG.UserManagement.user.persistance.mapper;

import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

public interface UserDAOMapper {
    UserDAO createUserDAO(User user);
    User createUser(UserDAO user);
}
