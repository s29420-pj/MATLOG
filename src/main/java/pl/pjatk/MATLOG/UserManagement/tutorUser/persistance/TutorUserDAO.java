package pl.pjatk.MATLOG.userManagement.tutorUser.persistance;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "tutor_user")
public class TutorUserDAO {

    @Id
    String id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(unique = true ,nullable = false)
    String emailAddress;

    @Column(nullable = false)
    String password;

    @Column()
    LocalDate dateOfBirth;

    @Column
    @ElementCollection(targetClass = GrantedAuthority.class)
    Set<GrantedAuthority> authorities;

    @Column
    Boolean isAccountNonLocked;

    @Column
    String biography;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = SchoolSubject.class)
    Set<SchoolSubject> specializations;

    @OneToMany
    Set<ReviewDAO> reviews;

    protected TutorUserDAO() {
    }

    TutorUserDAO(String id,
                 String firstName,
                 String lastName,
                 String emailAddress,
                 String password,
                 LocalDate dateOfBirth,
                 Set<GrantedAuthority> authorities,
                 Boolean isAccountNonLocked,
                 String biography,
                 Set<SchoolSubject> specializations,
                 Set<ReviewDAO> reviews) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.authorities = authorities;
        this.isAccountNonLocked = isAccountNonLocked;
        this.biography = biography;
        this.specializations = specializations;
        this.reviews = reviews;
    }
}
