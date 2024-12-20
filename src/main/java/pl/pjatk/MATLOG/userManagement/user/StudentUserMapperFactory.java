package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.StudentUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.StudentUserDAOMapper;

@Component
public class StudentUserMapperFactory implements UserMapperFactory {

    private final StudentUserDAOMapper userDAOMapper;
    private final StudentUserDTOMapper userDTOMapper;

    public StudentUserMapperFactory(StudentUserDAOMapper userDAOMapper,
                                    StudentUserDTOMapper userDTOMapper) {
        this.userDAOMapper = userDAOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public StudentUserDAOMapper getUserDAOMapper() {
        return userDAOMapper;
    }

    @Override
    public UserDTOMapper getUserDTOMapper() {
        return userDTOMapper;
    }
}
