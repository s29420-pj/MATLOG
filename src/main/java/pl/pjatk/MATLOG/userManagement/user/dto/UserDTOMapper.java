package pl.pjatk.MATLOG.UserManagement.user.dto;

import pl.pjatk.MATLOG.Domain.User;

/**
 * Interface which represents how minimal UserDTOMapper should look like.
 */
public interface UserDTOMapper {

    User createUser(UserDTO userDTO);
}
