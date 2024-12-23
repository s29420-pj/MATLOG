package pl.pjatk.MATLOG.userManagement.user.tutor.persistance;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.userManagement.user.persistance.UserDAO;

import java.time.LocalDate;
import java.util.Set;

@Document("tutor_user")
public record TutorUserDAO(@MongoId String id,
                           String firstName,
                           String lastName,
                           String emailAddress,
                           String password,
                           LocalDate dateOfBirth,
                           String biography,
                           Set<SchoolSubject> specializations,
                           Set<GrantedAuthority> authorities,
                           Boolean isAccountNonLocked) implements UserDAO {

    public TutorUserDAO {}
}