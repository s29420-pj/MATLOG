package pl.pjatk.MATLOG.Domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TutorUserTests {

    private final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

    // ------------------ happy tests ----------------------

    @Test
    void createTutorUser() {
        TutorUser tutor = TutorUser.builder()
                .withFirstName("Emily")
                .withLastName("Rose")
                .withEmailAddress("example@example.com")
                .withPassword("testP@ssword", userPasswordValidator)
                .withBiography("Im happy")
                .withIsAccountNonLocked(true)
                .withDateOfBirth(LocalDate.now().minusYears(31))
                .build();
        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertEquals("Emily", tutor.getFirstName());
            assertEquals("Rose", tutor.getLastName());
            assertEquals("Emily Rose", tutor.getFullName());
            assertEquals("example@example.com", tutor.getEmailAddress());
            assertEquals("testP@ssword", tutor.getPassword());
            assertEquals("Im happy", tutor.getBiography());
            assertEquals(31, tutor.getAge());
            assertTrue(tutor.isAccountNonLocked());
            assertTrue(tutor.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
            assertTrue(tutor.getAuthorities().contains(new SimpleGrantedAuthority("TUTOR_USER")));
        });
    }

    @Test
    void createTutorUserWithProvidedAuthority() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("TUTOR_USER"));
        TutorUser tutor = TutorUser.builder()
                .withFirstName("Emily")
                .withLastName("Rose")
                .withEmailAddress("example@example.com")
                .withPassword("testP@ssword", userPasswordValidator)
                .withBiography("Im happy")
                .withIsAccountNonLocked(true)
                .withAuthorities(authorities)
                .build();

        assertAll(() -> {
            assertNotNull(tutor.getAuthorities());
            assertTrue(tutor.getAuthorities().contains(new SimpleGrantedAuthority("TUTOR_USER")));
            assertTrue(tutor.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
        });
    }

    @Test
    void createTutorWithEmptyBiography() {
        TutorUser tutor = TutorUser.builder()
                .withFirstName("Emily")
                .withLastName("Rose")
                .withEmailAddress("example@example.com")
                .withPassword("testP@ssword", userPasswordValidator)
                .build();

        assertEquals("", tutor.getBiography());
    }

    @Test
    void createTutorWithProvidedSpecializations() {
        Set<SchoolSubject> specializations = new HashSet<>();
        specializations.add(SchoolSubject.MATHEMATICS);

        TutorUser tutor = TutorUser.builder()
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .withSpecializations(specializations)
                .build();

        assertAll(() -> {
            assertNotNull(tutor.getSpecializations());
            assertTrue(tutor.getSpecializations().contains(SchoolSubject.MATHEMATICS));
            assertFalse(tutor.getSpecializations().contains(SchoolSubject.LOGIC));
        });
    }

    // ----------------- TutorUser specialization tests ------------------------

    @Test
    void addSpecializationToTutorUser() {
        String uuid = UUID.randomUUID().toString();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();

        tutor.addSpecializationItem(SchoolSubject.MATHEMATICS);

        assertTrue(tutor.getSpecializations().contains(SchoolSubject.MATHEMATICS));
    }

    @Test
    void addCollectionOfSpecializationsToTutorUser() {
        String uuid = UUID.randomUUID().toString();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();


        tutor.addSpecializationItem(List.of(SchoolSubject.LOGIC, SchoolSubject.MATHEMATICS));

        assertAll(() -> {
            assertNotNull(tutor.getSpecializations());
            assertTrue(tutor.getSpecializations().contains(SchoolSubject.LOGIC));
            assertTrue(tutor.getSpecializations().contains(SchoolSubject.MATHEMATICS));
        });
    }

    @Test
    void removeSpecializationFromTutorUser() {
        String uuid = UUID.randomUUID().toString();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();

        tutor.addSpecializationItem(SchoolSubject.MATHEMATICS);

        tutor.removeSpecializationItem(SchoolSubject.MATHEMATICS);

        assertTrue(tutor.getSpecializations().isEmpty());
    }

    @Test
    void removeCollectionOfSpecializationsFromTutorUser() {
        String uuid = UUID.randomUUID().toString();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();


        tutor.addSpecializationItem(List.of(SchoolSubject.LOGIC, SchoolSubject.MATHEMATICS));

        tutor.removeSpecializationItem(List.of(SchoolSubject.LOGIC, SchoolSubject.MATHEMATICS));

        assertTrue(tutor.getSpecializations().isEmpty());
    }

    // ----------------- TutorUser biography tests ------------------------

    @Test
    void changeBiographyOfTutorUser() {
        TutorUser tutor = TutorUser.builder()
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withBiography("test")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();

        tutor.changeBiography("Hello");

        assertEquals("Hello", tutor.getBiography());
    }
}
