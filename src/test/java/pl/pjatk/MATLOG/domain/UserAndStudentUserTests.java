package pl.pjatk.MATLOG.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.exceptions.userExceptions.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserAndStudentUserTests {

    // ------------------ happy tests ----------------------

    @Test
    void createStudentUser() {
        User student = StudentUser.builder()
                .withFirstName("Ethan")
                .withLastName("Hovermann")
                .withEmailAddress("example@example.com")
                .withPassword("testPassword!")
                .withDateOfBirth(LocalDate.now().minusYears(50))
                .build();
        assertAll(() -> {
            assertNotNull(student.getId());
            assertEquals("Ethan Hovermann", student.getFullName());
            assertEquals(50, student.getAge());
            assertEquals("Ethan", student.getFirstName());
            assertEquals("Hovermann", student.getLastName());
            assertEquals("testPassword!", student.getPassword());
            assertEquals("example@example.com", student.getEmailAddress());
        });
    }

    @Test
    void ageEqualsTo100DateOfBirthStudent() {
        User studentUser = new StudentUser.StudentUserBuilder()
                .withFirstName("Ethan")
                .withLastName("Hovermann")
                .withEmailAddress("test@example.com")
                .withPassword("testP@ssword!")
                .withDateOfBirth(LocalDate.now().minusYears(100))
                .build();
        assertAll(() -> {
            assertNotNull(studentUser.getId());
            assertEquals("Ethan", studentUser.getFirstName());
            assertEquals("Hovermann", studentUser.getLastName());
            assertEquals("Ethan Hovermann", studentUser.getFullName());
            assertEquals("test@example.com", studentUser.getEmailAddress());
            assertEquals("testP@ssword!", studentUser.getPassword());
            assertEquals(100, studentUser.getAge());
        });
    }

    @Test
    void noDateOfBirthStudent() {
        User studentUser = new StudentUser.StudentUserBuilder()
                .withFirstName("Mark")
                .withLastName("Twain")
                .withEmailAddress("example@example.com")
                .withPassword("testP@ssword")
                .build();
        assertAll(() -> {
            assertNotNull(studentUser.getId());
            assertEquals("Mark Twain", studentUser.getFullName());
            assertEquals("example@example.com", studentUser.getEmailAddress());
            assertEquals(-1, studentUser.getAge());
        });
    }

    // ------------------ first name tests ----------------------

    @Test
    void nullFirstNameStudent() {
        assertThrows(UserInvalidFirstNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName(null)
                    .build();
        });
    }

    @Test
    void blankFirstNameStudent() {
        assertThrows(UserInvalidFirstNameException.class, () -> {
            StudentUser.builder()
                    .withFirstName("")
                    .build();
        });
    }

    @Test
    void firstNameNotSetInBuilder() {
        assertThrows(IllegalStateException.class, () -> {
            StudentUser.builder()
                    .withLastName("Evans")
                    .withEmailAddress("test@example.com")
                    .withPassword("P@ssword!")
                    .build();
        });
    }

    // ------------------ last name tests ----------------------

    @Test
    void nullLastNameStudent() {
        assertThrows(IllegalStateException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withDateOfBirth(LocalDate.of(2003, 12, 12))
                    .build();
        });
    }

    @Test
    void blankLastNameStudent() {
        assertThrows(IllegalStateException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .build();
        });
    }

    // ------------------ email address tests ------------------------

    @Test
    void nullEmailAddressStudent() {
        assertThrows(IllegalStateException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withDateOfBirth(LocalDate.of(1990, 3, 12))
                    .build();
        });
    }

    @Test
    void blankEmailAddressStudent() {
        assertThrows(IllegalStateException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withDateOfBirth(LocalDate.of(1965, 4, 19))
                    .build();
        });
    }

    // ------------------ date of birth tests ------------------------

    @Test
    @DisplayName("Throws exception when date of birth is null")
    void nullDateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withDateOfBirth(null)
                    .build();
        });
    }

    @Test
    @DisplayName("Throws exception when student is 0 years old")
    void ageEqualsTo0DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withDateOfBirth(LocalDate.now())
                    .build();
        });
    }

    @Test
    @DisplayName("Throws exception when student is below 0 years old")
    void ageBelow0DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withDateOfBirth(LocalDate.now().plusYears(3))
                    .build();
        });
    }

    @Test
    @DisplayName("Throws exception when student is above 100 years old")
    void ageGreaterThan100DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withDateOfBirth(LocalDate.now().minusYears(101))
                    .build();
        });
    }

}
