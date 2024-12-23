package pl.pjatk.MATLOG.userManagement.securityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebAuthorizationConfiguration {

    private final AuthenticationProvider authenticationProvider;

    public WebAuthorizationConfiguration(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .formLogin(e -> e
                        .successForwardUrl("/block"))
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/student/user/controller/register").permitAll()
                            .requestMatchers("tutor/user/controller/register").permitAll()
                            .requestMatchers("/hello").permitAll()
                            .anyRequest().authenticated()
                );
        return http.build();
    }
}
