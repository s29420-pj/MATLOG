package pl.pjatk.MATLOG.userManagement.unitTests;

import org.junit.jupiter.api.Test;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.SecurityUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityUserTests {

    @Test
    void createSecurityUser() {
        User student = StudentUser.builder()
                .withFirstName("Anna")
                .withLastName("Walker")
                .withEmailAddress("test@example.com")
                .withPassword("!pAsswer2")
                .withDateOfBirth(LocalDate.now().minusYears(23))
                .build();
        SecurityUser secUser = new SecurityUser(student);
        assertAll(() -> {
            assertEquals(student.getEmailAddress(), secUser.getUsername());
            assertEquals(student.getPassword(), secUser.getPassword());
            assertEquals(student.getAuthorities(), secUser.getAuthorities());
            assertEquals(student.isAccountNonLocked(), secUser.isAccountNonLocked());
        });
    }
}
