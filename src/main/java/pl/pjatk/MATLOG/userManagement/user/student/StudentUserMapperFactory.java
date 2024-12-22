package pl.pjatk.MATLOG.UserManagement.user.student;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDAOMapper;

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
