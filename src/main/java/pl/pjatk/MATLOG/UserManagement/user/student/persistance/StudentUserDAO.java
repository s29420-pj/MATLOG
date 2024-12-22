package pl.pjatk.MATLOG.UserManagement.user.student.persistance;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;

import java.time.LocalDate;
import java.util.Set;

/**
 * Class that is used to communicate with database through repository layer.
 * @param id identifier of a student.
 * @param firstName first name of a student.
 * @param lastName last name of a student.
 * @param emailAddress email address of a student.
 * @param password password of a student.
 * @param dateOfBirth date of birth of a student.
 * @param authorities authorities (group permissions) of a student.
 * @param isAccountNonLocked is student account not locked.
 */
@Document("student_user")
public record StudentUserDAO(@MongoId String id,
                      String firstName,
                      String lastName,
                      String emailAddress,
                      String password,
                      LocalDate dateOfBirth,
                      Set<GrantedAuthority> authorities,
                      Boolean isAccountNonLocked) implements UserDAO {

    public StudentUserDAO {}
}
