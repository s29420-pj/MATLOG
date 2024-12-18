package pl.pjatk.MATLOG.UserManagement.user.dto.mapper;

import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;

public interface RegisterUserDTOMapper {

    default User getUser(UserDTO userDTO) {
        return createUser(userDTO);
    }

    User createUser(UserDTO userDTO);
}
