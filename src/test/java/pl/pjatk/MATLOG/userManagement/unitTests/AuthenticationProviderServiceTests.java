package pl.pjatk.MATLOG.userManagement.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.AuthenticationProviderService;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.MatlogUserDetailsService;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.user.SecurityUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationProviderServiceTests {

    private final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

    @Mock
    private MatlogUserDetailsService userDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityContextHolder context;
    @InjectMocks
    private AuthenticationProviderService authenticationProviderService;

    @Test
    void authenticate() {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("test@example.com");
        when(auth.getCredentials()).thenReturn("P@ssword12!");

        SecurityUser user = new SecurityUser(StudentUser.builder()
                .withFirstName("Geroge")
                .withLastName("Stephen")
                .withEmailAddress("test@example.com")
                .withDateOfBirth(LocalDate.now().minusYears(19))
                .withPassword("P@ssword12!", userPasswordValidator)
                .build());
        when(userDetailsService.loadUserByUsername("test@example.com"))
                .thenReturn(user);
        when(passwordEncoder.matches("P@ssword12!", user.getPassword())).thenReturn(true);

        Authentication token = authenticationProviderService.authenticate(auth);
        assertAll(() -> {
            assertEquals(user.getUsername(), token.getPrincipal());
            assertEquals(user.getPassword(), token.getCredentials().toString());
        });

    }
}
