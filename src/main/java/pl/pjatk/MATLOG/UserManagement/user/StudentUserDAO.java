package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.PrivateLesson;

import java.time.LocalDate;
import java.util.Set;

@Document("student_user")
public record StudentUserDAO(@MongoId String id,
                             String firstName,
                             String lastName,
                             String emailAddress,
                             String password,
                             LocalDate dateOfBirth,
                             Set<GrantedAuthority> authorities,
                             boolean isAccountNonLocked,
                             Role role,
                             Set<PrivateLesson> privateLessons) {

    public StudentUserDAO {}
}