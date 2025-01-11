package pl.pjatk.MATLOG.userManagement.tutorUser.persistance;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

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
    @Fetch(value = FetchMode.JOIN)
    @ElementCollection(targetClass = GrantedAuthority.class)
    Set<GrantedAuthority> authorities;

    @Column
    Boolean isAccountNonLocked;

    @Column
    String biography;

    @Enumerated(EnumType.STRING)
    @Fetch(value = FetchMode.JOIN)
    @ElementCollection(targetClass = SchoolSubject.class)
    Set<SchoolSubject> specializations;

    @OneToMany(cascade = CascadeType.ALL)
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
