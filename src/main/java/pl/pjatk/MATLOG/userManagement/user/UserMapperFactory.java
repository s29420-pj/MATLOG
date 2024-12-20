package pl.pjatk.MATLOG.UserManagement.user;

import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.UserDAOMapper;

public interface UserMapperFactory {

    UserDAOMapper getUserDAOMapper();
    UserDTOMapper getUserDTOMapper();
}
