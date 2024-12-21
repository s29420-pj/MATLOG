package pl.pjatk.MATLOG.UserManagement.user.persistance;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Set;

@Document("tutor_user")
public record TutorUserDAO(@MongoId String id,
                           String firstName,
                           String lastName,
                           String emailAddress,
                           String password,
                           LocalDate dateOfBirth,
                           Set<GrantedAuthority> authorities,
                           Boolean isAccountNonLocked) implements UserDAO{

    public TutorUserDAO {}
}
