package pl.pjatk.MATLOG.userManagement.user.dto;

import pl.pjatk.MATLOG.domain.User;

/**
 * Interface which represents how minimal UserDTOMapper should look like.
 */
public interface UserDTOMapper {

    User createUser(UserDTO userDTO);
}
