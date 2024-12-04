package pl.pjatk.MATLOG.userManagement.integrationTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import pl.pjatk.MATLOG.TestInfrastructure;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestInfrastructure.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void beforeAll() {
        userRepository.saveAll(generateUsers());
    }

    private static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        User user1 = StudentUser.builder()
                .withFirstName("Eric")
                .withLastName("Gonzalez")
                .withEmailAddress("example@ex.com")
                .withPassword("testPassword!")
                .withDateOfBirth(LocalDate.of(2001, 10, 10))
                .build();
        users.add(user1);
        return users;
    }

    @AfterAll
    void afterAll() {
        userRepository.deleteAll();
    }
    @Test
    void returnValidUser() {
        Optional<User> userFromDb = userRepository.findByEmailAddress("example@ex.com");

        assertAll(() -> {
            assertTrue(userFromDb.isPresent());
            assertEquals("Matt",userFromDb.get().getFirstName());
        });
    }
}
