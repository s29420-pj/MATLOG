package pl.pjatk.MATLOG.UserManagement.user.persistance.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.PrivateLessonManagment.PrivateLessonService;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

@Component
public class StudentUserDAOMapper implements UserDAOMapper{

    private final PrivateLessonService privateLessonService;

    public StudentUserDAOMapper(PrivateLessonService privateLessonService) {
        this.privateLessonService = privateLessonService;
    }

    @Override
    public StudentUserDAO createUserDAO(User user) {
        return new StudentUserDAO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked());
    }

    @Override
    public StudentUser createUser(UserDAO user) {
        return StudentUser.builder()
                .withId(user.id())
                .withFirstName(user.firstName())
                .withLastName(user.lastName())
                .withEmailAddress(user.emailAddress())
                .withPassword(user.password())
                .withDateOfBirth(user.dateOfBirth())
                .withAuthorities(user.authorities())
                .withIsAccountNonLocked(user.isAccountNonLocked())
                .withPrivateLessons(privateLessonService.findByStudentId(user.id()))
                .build();
    }
}
