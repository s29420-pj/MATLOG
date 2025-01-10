package pl.pjatk.MATLOG.userManagement.studentUser.persistance;

import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

/**
 * Mapper that is used to map StudentUser to StudentUserDAO and vice versa.
 */
@Mapper
public class StudentUserDAOMapper {

    private final UserPasswordValidator userPasswordValidator;

    public StudentUserDAOMapper(UserPasswordValidator userPasswordValidator) {
        this.userPasswordValidator = userPasswordValidator;
    }

    /**
     * Method that maps User to StudentUserDAO which can be saved in database
     * @param user User representation of StudentUser
     * @return StudentUserDAO
     */
    public StudentUserDAO mapToDAO(StudentUser user) {
        return new StudentUserDAO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAuthorities(),
                user.isAccountNonLocked()
        );
    }

    /**
     * Method that maps UserDAO to StudentUser which can be used in application.
     * @param user UserDAO which is database representation.
     * @return StudentUser
     */
    public StudentUser mapToDomain(StudentUserDAO user) {
        return StudentUser.builder()
                .withId(user.id)
                .withFirstName(user.firstName)
                .withLastName(user.lastName)
                .withEmailAddress(user.emailAddress)
                .withPassword(user.password, userPasswordValidator)
                .withDateOfBirth(user.dateOfBirth)
                .withAuthorities(user.authorities)
                .withIsAccountNonLocked(user.isAccountNonLocked)
                .build();
    }

    public StudentUserDAO mapToDAO(StudentUserReviewLookUpDTO studentUserReviewLookUpDTO) {
        StudentUserDAO studentUserDAO = new StudentUserDAO();
        studentUserDAO.id = studentUserReviewLookUpDTO.id();
        studentUserDAO.firstName = studentUserReviewLookUpDTO.firstName();
        return studentUserDAO;
    }
}
