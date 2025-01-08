package pl.pjatk.MATLOG.UserManagement.studentUser.persistance;

import jakarta.persistence.Column;
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
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;

    @Column()
    private LocalDate dateOfBirth;
    private Set<GrantedAuthority> authorities;
    private Boolean isAccountNonLocked;

}
