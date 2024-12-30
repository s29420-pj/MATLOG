package pl.pjatk.MATLOG.UserManagement.user.student;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserReviewDTOMapper;

@Component
public class StudentUserMapperFactory {

    private final StudentUserDAOMapper userDAOMapper;
    private final StudentUserDTOMapper userDTOMapper;
    private final StudentUserReviewDTOMapper studentUserReviewDTOMapper;

    public StudentUserMapperFactory(StudentUserDAOMapper userDAOMapper,
                                    StudentUserDTOMapper userDTOMapper,
                                    StudentUserReviewDTOMapper studentUserReviewDTOMapper) {
        this.userDAOMapper = userDAOMapper;
        this.userDTOMapper = userDTOMapper;
        this.studentUserReviewDTOMapper = studentUserReviewDTOMapper;
    }

    public StudentUserDAOMapper getUserDAOMapper() {
        return userDAOMapper;
    }

    public StudentUserDTOMapper getUserDTOMapper() {
        return userDTOMapper;
    }

    public StudentUserReviewDTOMapper getStudentUserReviewDTOMapper() {
        return studentUserReviewDTOMapper;
    }
}
