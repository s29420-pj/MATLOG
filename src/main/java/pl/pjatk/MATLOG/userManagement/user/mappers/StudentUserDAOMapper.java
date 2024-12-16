package pl.pjatk.MATLOG.UserManagement.user.mappers;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.PrivateLessonManagment.PrivateLessonService;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserDAO;

@Component
public class StudentUserDAOMapper {

    private final PrivateLessonService privateLessonService;

    public StudentUserDAOMapper(PrivateLessonService privateLessonService) {
        this.privateLessonService = privateLessonService;
    }

    public StudentUserDAO createStudentUserDAO(StudentUser user) {

    }
}
