package pl.pjatk.MATLOG.UserManagement.tutorUser.persistance;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

import java.util.Set;

import java.time.LocalDate;

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

    @Column(columnDefinition = "String[]")
    private Set<GrantedAuthority> authorities;

    private Boolean isAccountNonLocked;

    private String biography;

    @Column(columnDefinition = "String[]")
    private Set<SchoolSubject> schoolSubjects;

    @OneToMany
    private Set<ReviewDAO> reviews;

}
