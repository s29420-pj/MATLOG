package pl.pjatk.MATLOG.UserManagement.user.mappers;

import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.UserManagement.user.persistance.StudentUserDAO;

public class StudentUserDAOMapper {

    public StudentUser mapTo(StudentUserDAO user) {
        return mapStudentUserDaoToStudentUser(user);
    }

    public StudentUserDAO mapFrom(StudentUser user) {
        return mapStudentUserToStudentUserDao(user);
    }

    private StudentUser mapStudentUserDaoToStudentUser(StudentUserDAO studentUserDAO) {
        return StudentUser.builder()
                .withFirstName(studentUserDAO.firstName())
                .withLastName(studentUserDAO.lastName())
                .withEmailAddress(studentUserDAO.emailAddress())
                .withPassword(studentUserDAO.password())
                .withDateOfBirth(studentUserDAO.dateOfBirth())
                .withAuthorities(studentUserDAO.authorities())
                .withIsAccountNonLocked(studentUserDAO.isAccountNonLocked())
                .withPrivateLessons(studentUserDAO.privateLessons())
                .build();
    }

    private StudentUserDAO mapStudentUserToStudentUserDao(StudentUser studentUser) {
        return new StudentUserDAO(studentUser.getId(),
                studentUser.getFirstName(),
                studentUser.getLastName(),
                studentUser.getEmailAddress(),
                studentUser.getPassword(),
                studentUser.getDateOfBirth(),
                studentUser.getAuthorities(),
                studentUser.isAccountNonLocked(),
                studentUser.getRole(),
                studentUser.getPrivateLessons());
    }
}
