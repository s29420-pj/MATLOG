package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.StudentUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.StudentUserDAOMapper;

@Component
public class StudentUserMapperFactory {

    private final StudentUserDAOMapper userDAOMapper;
    private final StudentUserDTOMapper userDTOMapper;

    public StudentUserMapperFactory(StudentUserDAOMapper userDAOMapper,
                                    StudentUserDTOMapper userDTOMapper) {
        this.userDAOMapper = userDAOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    public StudentUserDAOMapper getUserDAOMapper() {
        return userDAOMapper;
    }

    public StudentUserDTOMapper getUserDTOMapper() {
        return userDTOMapper;
    }
}
