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
        User student = new StudentUser.StudentUserBuilder("Ethan", "Hovermann", "example@example.com")
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
        User studentUser = new StudentUser.StudentUserBuilder("Mark", "Twain", "example@example.com")
                .withDateOfBirth(LocalDate.now().minusYears(100))
                .build();
        assertAll(() -> {

        });
    }

    @Test
    void ageEqualsTo13DateOfBirthStudent() {
        User studentUser = new StudentUser.StudentUserBuilder("Mark", "Twain", "example@example.com")
                .withDateOfBirth(LocalDate.now().minusYears(13))
                .build();
        assertAll(() -> {
            assertNotNull(studentUser.getId());
            assertEquals("Mark Twain", studentUser.getFullName());
            assertEquals("example@example.com", studentUser.getEmailAddress());
            assertEquals(13, studentUser.getAge());
        });
    }

    @Test
    void noDateOfBirthStudent() {
        User studentUser = new StudentUser.StudentUserBuilder("Mark", "Twain", "example@example.com")
                .build();
        assertAll(() -> {
            assertNotNull(studentUser.getId());
            assertEquals("Mark Twain", studentUser.getFullName());
            assertEquals("example@example.com", studentUser.getEmailAddress());
            assertThrows(UserInvalidDateOfBirthException.class, studentUser::getAge);
        });
    }

    // ------------------ first name tests ----------------------

    @Test
    void nullFirstNameStudent() {
        assertThrows(UserInvalidFirstNameException.class, () -> {new StudentUser.StudentUserBuilder(null, "Evann", "test@example.com")
                .withDateOfBirth(LocalDate.of(2000, 4, 1))
                .build();
        });
    }

    @Test
    void blankFirstNameStudent() {
        assertThrows(UserInvalidFirstNameException.class, () -> {
            new StudentUser.StudentUserBuilder("", "Evann", "example@example.com")
                    .build();
        });
    }

    // ------------------ last name tests ----------------------

    @Test
    void nullLastNameStudent() {
        assertThrows(UserInvalidLastNameException.class, () -> {
            new StudentUser.StudentUserBuilder("Philip", null, "test@example.com")
                    .withDateOfBirth(LocalDate.of(2003, 12, 12))
                    .build();
        });
    }

    @Test
    void blankLastNameStudent() {
        assertThrows(UserInvalidLastNameException.class, () -> {
            new StudentUser.StudentUserBuilder("Mark", "", "example@example.com")
                    .build();
        });
    }

    // ------------------ email address tests ------------------------

    @Test
    void nullEmailAddressStudent() {
        assertThrows(UserInvalidEmailAddressException.class, () -> {
            new StudentUser.StudentUserBuilder("Mark", "Twain", null)
                    .withDateOfBirth(LocalDate.of(1990, 3, 12))
                    .build();
        });
    }

    @Test
    void blankEmailAddressStudent() {
        assertThrows(UserInvalidEmailAddressException.class, () -> {
            new StudentUser.StudentUserBuilder("Gregory", "House", "")
                    .withDateOfBirth(LocalDate.of(1965, 4, 19))
                    .build();
        });
    }

    // ------------------ date of birth tests ------------------------

    @Test
    @DisplayName("Throws exception when date of birth is null")
    void nullDateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder("Mark", "Twain", "example@example.com")
                    .withDateOfBirth(null)
                    .build();
        });
    }

    @Test
    @DisplayName("Throws exception when student is 0 years old")
    void ageEqualsTo0DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder("Mark", "Twain", "example@example.com")
                    .withDateOfBirth(LocalDate.now())
                    .build();
        });
    }

    @Test
    @DisplayName("Throws exception when student is below 0 years old")
    void ageBelow0DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder("Mark", "Twain", "example@example.com")
                    .withDateOfBirth(LocalDate.now().plusYears(3))
                    .build();
        });
    }

    @Test
    @DisplayName("Throws exception when student is above 100 years old")
    void ageGreaterThan100DateOfBirthStudent() {
        assertThrows(UserInvalidDateOfBirthException.class, () -> {
            new StudentUser.StudentUserBuilder("Mark", "Twain", "example@example.com")
                    .withDateOfBirth(LocalDate.now().minusYears(101))
                    .build();
        });
    }

}
