package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.exceptions.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StudentUserTests {

    // ------------------ happy tests ----------------------

    @Test
    void createStudentUser() {
        User student = new StudentUser.StudentUserBuilder()
                .withFirstName("Ethan")
                .withLastName("Hovermann")
                .withEmailAddress("example@example.com")
                .withDateOfBirth(LocalDate.now().minusYears(50))
                .build();
        assertAll(() -> {
            assertNotNull(student.getId());
            assertEquals("Ethan Hovermann", student.getFullName());
            assertEquals(50, student.getAge());
            assertEquals("example@example.com", student.getEmailAddress());
        });
    }

    @Test
    void ageEqualsTo100DateOfBirthStudent() {
        User studentUser = new StudentUser.StudentUserBuilder()
                .withFirstName("Mark")
                .withLastName("Twain")
                .withEmailAddress("example@example.com")
                .withDateOfBirth(LocalDate.now().minusYears(100))
                .build();
        assertAll(() -> {

        });
    }

    @Test
    void ageEqualsTo13DateOfBirthStudent() {
        User studentUser = new StudentUser.StudentUserBuilder()
                .withFirstName("Mark")
                .withLastName("Twain")
                .withEmailAddress("example@example.com")
                .withDateOfBirth(LocalDate.now().minusYears(13))
                .build();
        assertAll(() -> {
            assertNotNull(studentUser.getId());
            assertEquals("Mark Twain", studentUser.getFullName());
            assertEquals("example@example.com", studentUser.getEmailAddress());
            assertEquals(13, studentUser.getAge());
        });
    }

    // ------------------ first name tests ----------------------

    @Test
    void noFirstNameStudent() {
        assertThrows(UserInvalidFirstNameException.class, () -> {new StudentUser.StudentUserBuilder()
                .withLastName("Evann")
                .withEmailAddress("test@example.com")
                .withDateOfBirth(LocalDate.of(2000, 4, 1))
                .build();
        });
    }

    @Test
    void nullFirstNameStudent() {
        assertThrows(UserInvalidFirstNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName(null)
                    .withLastName("Evann")
                    .withEmailAddress("example@example.com")
                    .withDateOfBirth(LocalDate.of(2002, 5, 10))
                    .build();
        });
    }

    @Test
    void blankFirstNameStudent() {
        assertThrows(UserInvalidFirstNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("")
                    .build();
        });
    }

    // ------------------ last name tests ----------------------

    @Test
    void noLastNameStudent() {
        assertThrows(UserInvalidLastNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mike")
                    .withEmailAddress("mike@example.com")
                    .withDateOfBirth(LocalDate.of(1980, 9, 25))
                    .build();
        });
    }

    @Test
    void nullLastNameStudent() {
        assertThrows(UserInvalidLastNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Philip")
                    .withEmailAddress("test@example.com")
                    .withDateOfBirth(LocalDate.of(2003, 12, 12))
                    .build();
        });
    }

    @Test
    void blankLastNameStudent() {
        assertThrows(UserInvalidLastNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("")
                    .build();
        });
    }

    // ------------------ email address tests ------------------------

    @Test
    void noEmailAddressStudent() {
        assertThrows(UserInvalidEmailAddressException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("Twain")
                    .withDateOfBirth(LocalDate.of(1990, 3, 12))
                    .build();
        });
    }

    @Test
    void nullEmailAddressStudent() {
        assertThrows(UserInvalidEmailAddressException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Gregory")
                    .withLastName("House")
                    .withEmailAddress(null)
                    .withDateOfBirth(LocalDate.of(1965, 4, 19))
                    .build();
        });
    }

    @Test
    void blankEmailAddressStudent() {
        assertThrows(UserInvalidEmailAddressException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Gregory")
                    .withLastName("House")
                    .withEmailAddress("")
                    .withDateOfBirth(LocalDate.of(1965, 4, 19))
                    .build();
        });
    }

    // ------------------ date of birth tests ------------------------

    @Test
    void noDateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("Twain")
                    .withEmailAddress("example@example.com")
                    .build();
        });
    }

    @Test
    void nullDateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("Twain")
                    .withEmailAddress("example@example.com")
                    .withDateOfBirth(null)
                    .build();
        });
    }

    @Test
    void ageEqualsTo0DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("Twain")
                    .withEmailAddress("example@example.com")
                    .withDateOfBirth(LocalDate.now())
                    .build();
        });
    }

    @Test
    void ageBelow0DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("Twain")
                    .withEmailAddress("example@example.com")
                    .withDateOfBirth(LocalDate.now().plusYears(3))
                    .build();
        });
    }

    @Test
    void ageGreaterThan100DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("Twain")
                    .withEmailAddress("example@example.com")
                    .withDateOfBirth(LocalDate.now().minusYears(101))
                    .build();
        });
    }

    @Test
    void ageLessThan13DateOfBirthStudent() {
        assertThrows(UserAgeRestrictionException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Mark")
                    .withLastName("Twain")
                    .withEmailAddress("example@example.com")
                    .withDateOfBirth(LocalDate.now().minusYears(12))
                    .build();
        });
    }

}
