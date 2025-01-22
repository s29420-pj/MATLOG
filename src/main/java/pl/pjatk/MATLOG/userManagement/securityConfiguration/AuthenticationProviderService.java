package pl.pjatk.MATLOG.userManagement.securityConfiguration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.userManagement.user.SecurityUser;
import pl.pjatk.MATLOG.userManagement.user.dto.LoggedUserDTO;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

/**
 * Component that is responsible for handling data evaluation. It checks
 * if provided password is same as in the database then Authentication object is returned
 */
@Component
public class AuthenticationProviderService {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(LoggedUserDTO loggedUserDTO) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);
        return JWT.create()
                .withIssuer(loggedUserDTO.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("id", loggedUserDTO.getId())
                .withClaim("firstName", loggedUserDTO.getFirstName())
                .withClaim("lastName", loggedUserDTO.getLastName())
                .withClaim("emailAddress", loggedUserDTO.getUsername())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        LoggedUserDTO user = LoggedUserDTO.builder()
                .username(decoded.getIssuer())
                .id(decoded.getClaim("id").asString())
                .firstName(decoded.getClaim("firstName").asString())
                .lastName(decoded.getClaim("lastName").asString())
                .build();
        return new UsernamePasswordAuthenticationToken(user, null);
    }

}
