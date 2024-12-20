package pl.pjatk.MATLOG.UserManagement.user;

import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.UserDAOMapper;

public interface UserMapperFactory {

    UserDAOMapper userDAOMapper();
    UserDTOMapper userDTOMapper();
}
