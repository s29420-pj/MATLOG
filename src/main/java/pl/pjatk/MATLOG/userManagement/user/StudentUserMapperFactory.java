package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.UserDAOMapper;

@Component
public class StudentUserMapperFactory implements UserMapperFactory {

    private final UserDAOMapper userDAOMapper;
    private final UserDTOMapper userDTOMapper;

    public StudentUserMapperFactory(@Qualifier("studentUserDAOMapper") UserDAOMapper userDAOMapper,
                                    @Qualifier("") UserDTOMapper userDTOMapper) {
        this.userDAOMapper = userDAOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public UserDAOMapper getUserDAOMapper() {
        return userDAOMapper;
    }

    @Override
    public UserDTOMapper getUserDTOMapper() {
        return userDTOMapper;
    }
}
