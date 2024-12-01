package pl.pjatk.MATLOG.userManagement.integrationTests;

import org.junit.jupiter.api.Test;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ApplicationModuleTest
@Import(TestInfrastructure.class)
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void returnValidUser() {
        User user = StudentUser.builder()
                .withFirstName("Matt")
                .withLastName("Grett")
                .withEmailAddress("example@example.com")
                .withPassword("TestP@ssword!")
                .build();
        userRepository.save(user);
        Optional<User> userFromDb = userRepository.findByEmailAddress("example@example.com");

        assertAll(() -> {
            assertTrue(userFromDb.isPresent());
            assertEquals("Matt",userFromDb.get().getFirstName());
        });
    }
}
