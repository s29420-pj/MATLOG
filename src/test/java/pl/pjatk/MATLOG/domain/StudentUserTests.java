package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.exceptions.UserInvalidDataException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StudentUserTests {

    @Test
    void createStudentUser() {
        User student = new StudentUser.StudentUserBuilder()
                .withFirstName("Ethan")
                .withLastName("Hovermann")
                .withEmailAddress("example@example.com")
                .withDateOfBirth(LocalDate.of(1999, 12, 15))
                .build();
        assertAll(() -> {
            assertNotNull(student.getId());
            assertEquals("Ethan Hovermann", student.getFullName());
            assertEquals(LocalDate.now().getYear() - 1999, student.getAge());
            assertEquals("example@example.com", student.getEmailAddress());
        });
    }

    @Test
    void invalidFirstNameStudent() {
        assertThrows(UserInvalidDataException.class, () -> {new StudentUser.StudentUserBuilder()
                .withLastName("Evann")
                .withEmailAddress("test@example.com")
                .withDateOfBirth(LocalDate.of(2000, 4, 1))
                .build();
        });
    }
}
