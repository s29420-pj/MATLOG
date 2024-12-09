package pl.pjatk.MATLOG.UserManagement.securityConfiguration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.SecurityUser;

/**
 * Component that is responsible for handling data evaluation. It checks
 * if provided password is same as in the database then Authentication object is returned
 */
@Component
public class AuthenticationProviderService implements AuthenticationProvider {

    private final MongoUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationProviderService(MongoUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method that checks if provided raw password is same as password stored in the database.
     * If so returns UsernamePasswordAuthenticationToken.
     * If passwords doesn't match then BadCredentialsException is thrown
     * @param authentication the authentication request object.
     * @return Authentication object
     * @throws AuthenticationException when something goes wrong
     * @throws BadCredentialsException when passwords doesn't match
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        SecurityUser user = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    /**
     * Method that is used by AuthenticationManager to check if
     * AuthenticationProvider supports this kind of authentication method.
     * @param authentication authentication method
     * @return UsernamePasswordAuthenticationToken that tells, this AuthenticationProvider supports
     * username and password type of authentication.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
