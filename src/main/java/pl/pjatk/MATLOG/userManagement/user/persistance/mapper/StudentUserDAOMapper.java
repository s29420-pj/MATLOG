package pl.pjatk.MATLOG.UserManagement.user.persistance.mapper;

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
        return new StudentUserDAO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked());
    }

    public StudentUser createStudentUser(StudentUserDAO user) {
        return StudentUser.builder()
                .withId(user.id())
                .withFirstName(user.firstName())
                .withLastName(user.lastName())
                .withEmailAddress(user.emailAddress())
                .withPassword(user.password())
                .withDateOfBirth(user.dateOfBirth())
                .withAuthorities(user.authorities())
                .withIsAccountNonLocked(user.isAccountNonLocked())
                .build();
    }
}
