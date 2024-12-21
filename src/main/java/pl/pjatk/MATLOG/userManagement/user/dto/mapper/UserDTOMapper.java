package pl.pjatk.MATLOG.UserManagement.user.dto.mapper;

import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;

public interface UserDTOMapper {

    User createUser(UserDTO userDTO);
}
