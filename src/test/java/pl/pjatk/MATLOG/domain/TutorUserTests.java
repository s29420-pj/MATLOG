package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TutorUserTests {

    // ------------------ happy tests ----------------------

    @Test
    void createTutorUser() {
        TutorUser tutor = TutorUser.builder()
                .withFirstName("Emily")
                .withLastName("Rose")
                .withEmailAddress("example@example.com")
                .withPassword("testP@ssword")
                .withDateOfBirth(LocalDate.now().minusYears(31))
                .build();
        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertNotNull(tutor.getPrivateLessons());
            assertTrue(tutor.getPrivateLessons().isEmpty());
            assertEquals("Emily", tutor.getFirstName());
            assertEquals(Role.TUTOR, tutor.getRole());
            assertEquals("Rose", tutor.getLastName());
            assertEquals("Emily Rose", tutor.getFullName());
            assertEquals("example@example.com", tutor.getEmailAddress());
            assertEquals("testP@ssword", tutor.getPassword());
            assertEquals(31, tutor.getAge());
            assertTrue(tutor.isAccountNonLocked());
            assertTrue(tutor.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
            assertTrue(tutor.getAuthorities().contains(new SimpleGrantedAuthority("TUTOR_USER")));
        });
    }

    @Test
    void addPrivateLesson() {
        TutorUser tutor = TutorUser.builder()
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!")
                .withDateOfBirth(LocalDate.now().minusYears(21))
                .build();

        PrivateLesson privateLesson = PrivateLesson.builder()
                .withOwnerId(tutor.getId())
                .withStartTime(LocalDateTime.now().plusMonths(2))
                .withEndTime(LocalDateTime.now().plusMonths(2).plusHours(1))
                .build();

        boolean isAdded = tutor.addLesson(privateLesson);

        assertAll(() -> {
            assertFalse(tutor.getPrivateLessons().isEmpty());
            assertEquals(Role.TUTOR, tutor.getRole());
            assertTrue(isAdded);
            assertTrue(tutor.getPrivateLessons().contains(privateLesson));
        });
    }

    @Test
    void createTutorWithProvidedSet() {
        Set<Lesson> set = new HashSet<>();

        TutorUser tutor = TutorUser.builder()
                .withFirstName("Anthony")
                .withLastName("Emmaus")
                .withEmailAddress("example@example.com")
                .withPassword("!pAssword!")
                .withPrivateLessons(set)
                .build();

        PrivateLesson lesson = PrivateLesson.builder()
                .withOwnerId(tutor.getId())
                .withStartTime(LocalDateTime.now().plusDays(2))
                .withEndTime(LocalDateTime.now().plusDays(2).plusHours(1))
                .build();

        set.add(lesson);

        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertEquals("Anthony Emmaus", tutor.getFullName());
            assertEquals("example@example.com", tutor.getEmailAddress());
            assertEquals(Role.TUTOR, tutor.getRole());
            assertFalse(tutor.getPrivateLessons().isEmpty());
            assertTrue(tutor.getPrivateLessons().contains(lesson));
        });
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
                .withPassword("!pAssword!")
                .withReviews(reviews)
                .build();

        assertAll(() -> {
            assertNotNull(tutor.getId());
            assertEquals("Anthony Emmaus", tutor.getFullName());
            assertEquals("example@example.com", tutor.getEmailAddress());
            assertEquals(Role.TUTOR, tutor.getRole());
            assertFalse(tutor.getReviews().isEmpty());
            assertTrue(tutor.getReviews().contains(review));
        });
    }

}
