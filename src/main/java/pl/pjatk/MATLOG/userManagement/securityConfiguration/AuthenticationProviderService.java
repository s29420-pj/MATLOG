package pl.pjatk.MATLOG.userManagement.securityConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.domain.User;
import pl.pjatk.MATLOG.userManagement.user.SecurityUser;
import pl.pjatk.MATLOG.userManagement.user.UserRepositoryService;
import pl.pjatk.MATLOG.userManagement.user.dto.LoggedUserDTO;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Component that is responsible for handling data evaluation. It checks
 * if provided password is same as in the database then Authentication object is returned
 */
@Component
@RequiredArgsConstructor
public class AuthenticationProviderService {

    private final UserRepositoryService userRepositoryService;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private SecretKey signingKey;

    @PostConstruct
    protected void init() {
        this.signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(LoggedUserDTO loggedUserDTO) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);
        return Jwts.builder()
                .setSubject(loggedUserDTO.getUsername())
                .claim("id", loggedUserDTO.getId())
                .claim("firstName", loggedUserDTO.getFirstName())
                .claim("lastName", loggedUserDTO.getLastName())
                .claim("emailAddress", loggedUserDTO.getUsername())
                .claim("roles", loggedUserDTO.getRoles())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(signingKey)
                .compact();
    }

    public Authentication validateToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = claims.get("roles", List.class);

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        LoggedUserDTO user = LoggedUserDTO.builder()
                .username(claims.getSubject())
                .id(claims.get("id", String.class))
                .firstName(claims.get("firstName", String.class))
                .lastName(claims.get("lastName", String.class))
                .roles(roles)
                .build();

        User domainUser = userRepositoryService.findUserByEmailAddress(user.getUsername());

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

}
