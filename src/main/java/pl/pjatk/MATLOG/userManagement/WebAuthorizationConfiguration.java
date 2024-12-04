package pl.pjatk.MATLOG.userManagement;

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
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/user/controller/create").permitAll()
                            .requestMatchers("/hello").permitAll()
                            .requestMatchers("/user/controller/login").permitAll()
                            .anyRequest().authenticated()
                );
        return http.build();
    }
}
