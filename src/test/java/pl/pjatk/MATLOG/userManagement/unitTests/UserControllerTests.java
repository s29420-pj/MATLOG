package pl.pjatk.MATLOG.userManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.pjatk.MATLOG.domain.StudentUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void helloMessage() throws Exception {
        mvc.perform(post("/user/controller/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(StudentUser.builder()
                        .withFirstName("Matt")
                        .withLastName("Thew")
                        .withEmailAddress("example@example.com")
                        .withPassword("testPassword!")
                        .build()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
