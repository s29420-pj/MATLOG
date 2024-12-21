package pl.pjatk.MATLOG.Domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;
import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            assertNotNull(tutor.getPrivateLessons());
            assertTrue(tutor.getPrivateLessons().isEmpty());
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
    void createTutorWithProvidedReviews() {
        Review review = Review.create(Stars.TWO, "comment", UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
        List<Review> reviews = List.of(review);
        TutorUser tutor = TutorUser.builder()
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .withReviews(reviews)
                .build();

        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertEquals("Anthony Emmaus", tutor.getFullName());
            assertEquals("example@example.com", tutor.getEmailAddress());
            assertFalse(tutor.isAccountNonLocked());
            assertFalse(tutor.getReviews().isEmpty());
            assertTrue(tutor.getReviews().contains(review));
        });
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

    @Test
    void createTutorUserWithProvidedPrivateLessons() {
        String uuid = UUID.randomUUID().toString();
        Set<PrivateLesson> privateLessons = new HashSet<>();
        PrivateLesson lesson = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.MATHEMATICS))
                .withTutorId(uuid)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(true)
                .withStartTime(LocalDateTime.now().plusDays(3))
                .withEndTime(LocalDateTime.now().plusDays(3).plusHours(1))
                .withPrice(32.5)
                .build();

        privateLessons.add(lesson);

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .withPrivateLessons(privateLessons)
                .build();

        assertAll(() -> {
            assertNotNull(tutor.getPrivateLessons());
            assertFalse(tutor.getPrivateLessons().isEmpty());
            assertTrue(tutor.getPrivateLessons().contains(lesson));
        });
    }

    // ----------------- TutorUser private lessons tests ------------------------

    @Test
    void addPrivateLessonToTutorUserSet() {
        String uuid = UUID.randomUUID().toString();

        PrivateLesson lesson = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.MATHEMATICS))
                .withTutorId(uuid)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(true)
                .withStartTime(LocalDateTime.now().plusDays(3))
                .withEndTime(LocalDateTime.now().plusDays(3).plusHours(1))
                .withPrice(32.5)
                .build();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();

        tutor.addPrivateLesson(lesson);

        assertAll(() -> {
            assertNotNull(tutor.getPrivateLessons());
            assertTrue(tutor.getPrivateLessons().contains(lesson));
        });
    }

    @Test
    void addCollectionOfPrivateLessonsToTutorUser() {
        String uuid = UUID.randomUUID().toString();

        PrivateLesson lesson1 = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.MATHEMATICS))
                .withTutorId(uuid)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(true)
                .withStartTime(LocalDateTime.now().plusDays(3))
                .withEndTime(LocalDateTime.now().plusDays(3).plusHours(1))
                .withPrice(32.5)
                .build();

        PrivateLesson lesson2 = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.LOGIC))
                .withTutorId(uuid)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(true)
                .withStartTime(LocalDateTime.now().plusDays(5))
                .withEndTime(LocalDateTime.now().plusDays(5).plusHours(1))
                .withPrice(71.5)
                .build();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();

        tutor.addPrivateLesson(List.of(lesson1, lesson2));

        assertAll(() -> {
            assertNotNull(tutor.getPrivateLessons());
            assertTrue(tutor.getPrivateLessons().containsAll(List.of(lesson1, lesson2)));
        });
    }

    @Test
    void removePrivateLessonFromTutorUser() {
        String uuid = UUID.randomUUID().toString();

        PrivateLesson lesson = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.LOGIC))
                .withTutorId(uuid)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(true)
                .withStartTime(LocalDateTime.now().plusDays(5))
                .withEndTime(LocalDateTime.now().plusDays(5).plusHours(1))
                .withPrice(71.5)
                .build();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();

        tutor.addPrivateLesson(lesson);

        tutor.removePrivateLesson(lesson);

        assertAll(() -> {
            assertFalse(tutor.getPrivateLessons().contains(lesson));
            assertTrue(tutor.getPrivateLessons().isEmpty());
        });
    }

    @Test
    void removeCollectionOfPrivateLessonsFromTutorUser() {
        String uuid = UUID.randomUUID().toString();

        PrivateLesson lesson = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.LOGIC))
                .withTutorId(uuid)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(true)
                .withStartTime(LocalDateTime.now().plusDays(5))
                .withEndTime(LocalDateTime.now().plusDays(5).plusHours(1))
                .withPrice(71.5)
                .build();

        PrivateLesson lesson2 = PrivateLesson.builder()
                .withSchoolSubjects(List.of(SchoolSubject.LOGIC))
                .withTutorId(uuid)
                .withPrivateLessonStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(true)
                .withStartTime(LocalDateTime.now().plusDays(5))
                .withEndTime(LocalDateTime.now().plusDays(5).plusHours(1))
                .withPrice(71.5)
                .build();

        TutorUser tutor = TutorUser.builder()
                .withId(uuid)
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!", userPasswordValidator)
                .build();

        tutor.addPrivateLesson(lesson);
        tutor.addPrivateLesson(lesson2);

        tutor.removePrivateLesson(Set.of(lesson, lesson2));

        assertAll(() -> {
            assertFalse(tutor.getPrivateLessons().contains(lesson));
            assertTrue(tutor.getPrivateLessons().isEmpty());
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
