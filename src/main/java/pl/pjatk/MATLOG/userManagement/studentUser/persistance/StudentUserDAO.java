package pl.pjatk.MATLOG.userManagement.studentUser.persistance;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Set;

/**
 */
@Entity(name = "student_user")
public class StudentUserDAO {

    @Id
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "email_address", nullable = false)
    String emailAddress;

    @Column(name = "password", nullable = false, columnDefinition = "text")
    String password;

    @Column
    LocalDate dateOfBirth;

    @Column
    @ElementCollection(targetClass = GrantedAuthority.class)
    Set<GrantedAuthority> authorities;

    @Column
    Boolean isAccountNonLocked;

    protected StudentUserDAO() {
    }

    StudentUserDAO(String id,
                   String firstName,
                   String lastName,
                   String emailAddress,
                   String password,
                   LocalDate dateOfBirth,
                   Set<GrantedAuthority> authorities,
                   Boolean isAccountNonLocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.authorities = authorities;
        this.isAccountNonLocked = isAccountNonLocked;
    }
}
