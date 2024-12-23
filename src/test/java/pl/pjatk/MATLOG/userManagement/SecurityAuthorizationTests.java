package pl.pjatk.MATLOG.userManagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.pjatk.MATLOG.TestInfrastructure;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ApplicationModuleTest
@Import(TestInfrastructure.class)
@AutoConfigureMockMvc
public class SecurityAuthorizationTests {

    @Autowired
    private MockMvc mvc;


    @Test
    public void helloUnauthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }

    // Test to be added when successfully completed authentication and authorization mechanism
//    @Test
//    @WithUserDetails("student123@example.com")
//    public void helloAuthenticated() throws Exception {
//        mvc.perform(get("/hello"))
//                .andExpect(status().isOk());
//    }

}
