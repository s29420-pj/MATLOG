package pl.pjatk.MATLOG.userManagement.tutorUser;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "tutor_user")
public class TutorUserDAO {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", unique = true ,nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    @ElementCollection(targetClass = GrantedAuthority.class)
    private Set<GrantedAuthority> authorities;

    @Column
    private Boolean isAccountNonLocked;

    @Column
    private String biography;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = SchoolSubject.class)
    private Set<SchoolSubject> schoolSubjects;

    @OneToMany
    private Set<ReviewDAO> reviews;

}
