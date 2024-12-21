package pl.pjatk.MATLOG.Domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.Exceptions.UserExceptions.*;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserAndStudentUserTests {

    private final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

    // ------------------ happy tests ----------------------

    @Test
    void createStudentUser() {
        User student = StudentUser.builder()
                .withFirstName("Ethan")
                .withLastName("Hovermann")
                .withEmailAddress("example@example.com")
                .withPassword("testPassword!", userPasswordValidator)
                .withDateOfBirth(LocalDate.now().minusYears(50))
                .withIsAccountNonLocked(true)
                .build();
        assertAll(() -> {
            assertNotNull(student.getId());
            assertEquals("Ethan Hovermann", student.getFullName());
            assertEquals(50, student.getAge());
            assertEquals("Ethan", student.getFirstName());
            assertEquals("Hovermann", student.getLastName());
            assertEquals("testPassword!", student.getPassword());
            assertEquals("example@example.com", student.getEmailAddress());
            assertTrue(student.isAccountNonLocked());
            assertEquals(LocalDate.now().minusYears(50), student.getDateOfBirth());
            assertTrue(student.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
            assertTrue(student.getAuthorities().contains(new SimpleGrantedAuthority("STUDENT_USER")));
        });
    }

    @Test
    void createStudentUserWithProvidedRole() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("STUDENT_USER"));

        User student = StudentUser.builder()
                .withFirstName("Ethan")
                .withLastName("Hovermann")
                .withEmailAddress("example@example.com")
                .withPassword("testPassword!", userPasswordValidator)
                .withDateOfBirth(LocalDate.now().minusYears(50))
                .withIsAccountNonLocked(true)
                .withAuthorities(authorities)
                .build();

        assertAll(() -> {
            assertNotNull(student.getAuthorities());
            assertTrue(student.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
            assertTrue(student.getAuthorities().contains(new SimpleGrantedAuthority("STUDENT_USER")));
        });
    }

    @Test
    void ageEqualsTo100DateOfBirthStudent() {
        User studentUser = new StudentUser.StudentUserBuilder()
                .withFirstName("Ethan")
                .withLastName("Hovermann")
                .withEmailAddress("test@example.com")
                .withPassword("testP@ssword!", userPasswordValidator)
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
                .withPassword("testP@ssword", userPasswordValidator)
                .build();
        assertAll(() -> {
            assertNotNull(studentUser.getId());
            assertEquals("Mark Twain", studentUser.getFullName());
            assertEquals("example@example.com", studentUser.getEmailAddress());
            assertEquals(-1, studentUser.getAge());
        });
    }

    @Test
    void changePasswordStudent() {
        User studentUser = StudentUser.builder()
                .withFirstName("Mark")
                .withLastName("Twain")
                .withEmailAddress("example@example.com")
                .withPassword("testP@ssword", userPasswordValidator)
                .build();
        UserPasswordValidator validator = new StandardUserPasswordValidator();
        studentUser.changePassword("!09Acb", validator);
        assertAll(() -> {
            assertNotNull(studentUser.getId());
            assertEquals("Mark Twain", studentUser.getFullName());
            assertEquals("example@example.com", studentUser.getEmailAddress());
            assertEquals(-1, studentUser.getAge());
            assertEquals("!09Acb", studentUser.getPassword());
        });
    }

    // ------------------ id tests ----------------------

    @Test
    void setIdInBuilder() {
        User user = StudentUser.builder()
                .withId(UUID.randomUUID().toString())
                .withFirstName("Matthew")
                .withLastName("Johnatan")
                .withPassword("!PdaD#@dff", userPasswordValidator)
                .withEmailAddress("test@example.com")
                .build();

        assertNotNull(user.getId());
    }

    @Test
    void setNullIdInBuilder() {
        User user = StudentUser.builder()
                .withId(null)
                .withFirstName("Matthew")
                .withLastName("Johnatan")
                .withPassword("!PdaD#@dff", userPasswordValidator)
                .withEmailAddress("test@example.com")
                .build();

        assertNotNull(user.getId());
    }

    @Test
    void setEmptyIdInBuilder() {
        User user = StudentUser.builder()
                .withId("")
                .withFirstName("Matthew")
                .withLastName("Johnatan")
                .withPassword("!PdaD#@dff", userPasswordValidator)
                .withEmailAddress("test@example.com")
                .build();

        assertNotNull(user.getId());
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
                    .withPassword("P@ssword!", userPasswordValidator)
                    .build();
        });
    }

    // ------------------ last name tests ----------------------

    @Test
    void nullLastNameStudent() {
        assertThrows(UserInvalidLastNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withLastName(null)
                    .withDateOfBirth(LocalDate.of(2003, 12, 12))
                    .build();
        });
    }

    @Test
    void blankLastNameStudent() {
        assertThrows(UserInvalidLastNameException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withLastName("")
                    .build();
        });
    }

    @Test
    void lastNameNotSetInBuilder() {
        assertThrows(IllegalStateException.class, () -> {
            StudentUser.builder()
                    .withFirstName("Roman")
                    .withEmailAddress("test@example.com")
                    .withPassword("Test123!", userPasswordValidator)
                    .build();
        });
    }

    // ------------------ email address tests ------------------------

    @Test
    void nullEmailAddressStudent() {
        assertThrows(UserInvalidEmailAddressException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Comapn")
                    .withLastName("Gyurr")
                    .withEmailAddress(null)
                    .withPassword("Test!234", userPasswordValidator)
                    .withDateOfBirth(LocalDate.of(1990, 3, 12))
                    .build();
        });
    }

    @Test
    void blankEmailAddressStudent() {
        assertThrows(UserInvalidEmailAddressException.class, () -> {
            new StudentUser.StudentUserBuilder()
                    .withFirstName("Comapn")
                    .withLastName("Gyurr")
                    .withEmailAddress("")
                    .withPassword("Test!234", userPasswordValidator)
                    .withDateOfBirth(LocalDate.of(1965, 4, 19))
                    .build();
        });
    }

    @Test
    void emailAddressNotSetInBuilder() {
        assertThrows(IllegalStateException.class, () -> {
            StudentUser.builder()
                    .withFirstName("Brian")
                    .withLastName("Connor")
                    .withPassword("@Pass!wor", userPasswordValidator)
                    .withDateOfBirth(LocalDate.of(1965, 4, 19))
                    .build();
        });
    }

    // ------------------ date of birth tests ------------------------

    @Test
    void nullDateOfBirthInBuilder() {
        User user = StudentUser.builder()
                .withFirstName("Brian")
                .withLastName("Connor")
                .withEmailAddress("example@com.pl")
                .withPassword("@Pass!wor", userPasswordValidator)
                .withDateOfBirth(null)
                .build();

        assertAll(() -> {
            assertNotNull(user.getId());
            assertEquals("Brian", user.getFirstName());
            assertEquals("Connor", user.getLastName());
            assertEquals("example@com.pl", user.getEmailAddress());
            assertNull(user.getDateOfBirth());
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

    // ------------------ password tests ------------------------

    @Test
    void passwordChangeUnsecure() {
        User user = StudentUser.builder()
                .withFirstName("Brian")
                .withLastName("Connor")
                .withEmailAddress("test@example.com")
                .withPassword("@Pass!wor", userPasswordValidator)
                .withDateOfBirth(LocalDate.of(1965, 4, 19))
                .build();

        assertThrows(UserUnsecurePasswordException.class, () -> {
            user.changePassword("dfs", userPasswordValidator);
        });
    }
    @Test
    void passwordNullInBuilder() {
        assertThrows(UserEmptyPasswordException.class, () -> {
            StudentUser.builder()
                    .withPassword(null, userPasswordValidator)
                    .build();
        });
    }

    @Test
    void passwordEmptyInBuilder() {
        assertThrows(UserEmptyPasswordException.class, () -> {
            StudentUser.builder()
                    .withPassword("", userPasswordValidator)
                    .build();
        });
    }

    @Test
    void passwordNotSetInBuilder() {
        assertThrows(IllegalStateException.class, () -> {
            StudentUser.builder()
                    .withFirstName("Gregory")
                    .withLastName("House")
                    .withEmailAddress("test@example.com")
                    .build();
        });
    }

    @Test
    void nullPasswordOnChangePassword() {
        User student = StudentUser.builder()
                        .withFirstName("Derek")
                                .withLastName("Terr")
                                        .withEmailAddress("test@example.com")
                                                .withPassword("!23esFFDP", userPasswordValidator)
                                                        .build();
        UserPasswordValidator validator = new StandardUserPasswordValidator();
        assertThrows(UserEmptyPasswordException.class, () -> {
            student.changePassword(null, validator);
        });
    }

    @Test
    void emptyPasswordOnChangePassword() {
        User student = StudentUser.builder()
                .withFirstName("Derek")
                .withLastName("Terr")
                .withEmailAddress("test@example.com")
                .withPassword("!23esFFDP", userPasswordValidator)
                .build();
        UserPasswordValidator validator = new StandardUserPasswordValidator();
        assertThrows(UserEmptyPasswordException.class, () -> {
            student.changePassword("", validator);
        });
    }

    @Test
    void unsecurePassword() {
        assertThrows(UserUnsecurePasswordException.class, () -> {
            StudentUser.builder()
                    .withFirstName("Derek")
                    .withLastName("Terr")
                    .withEmailAddress("test@example.com")
                    .withPassword("!23", userPasswordValidator)
                    .build();
        });
    }

    // ------------------ authorities tests ------------------------

    @Test
    void setAuthoritiesInBuilder() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("STUDENT"));
        authorities.add(new SimpleGrantedAuthority("USER"));

        User student = StudentUser.builder()
                .withFirstName("Jack")
                .withLastName("Harnn")
                .withEmailAddress("example@com.com")
                .withPassword("Tydses!223", userPasswordValidator)
                .withAuthorities(authorities)
                .build();
        assertAll(() -> {
            assertFalse(student.getAuthorities().isEmpty());
            assertTrue(student.getAuthorities().contains(new SimpleGrantedAuthority("STUDENT")));
            assertTrue(student.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
        });
    }
}
