package pl.pjatk.MATLOG.Domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.Enums.Stars;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
