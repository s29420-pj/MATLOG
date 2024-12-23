package pl.pjatk.MATLOG.userManagement.unitTests;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.AuthenticationProviderService;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.MongoUserDetailsService;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.StandardUserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.UserManagement.user.SecurityUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationProviderServiceTests {

    private final UserPasswordValidator userPasswordValidator = new StandardUserPasswordValidator();

    @Mock
    private MongoUserDetailsService userDetailsService;
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
