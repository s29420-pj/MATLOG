package pl.pjatk.MATLOG.configuration;

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
                .httpBasic(Customizer.withDefaults())
                .formLogin(e -> e
                        .successForwardUrl("/block"))
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/tutor/user/controller/register").permitAll()
                            .requestMatchers("/tutor/user/controller/get/profile/**").permitAll()
                            .requestMatchers("/tutor/user/controller/change/password/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/add/biography/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/add/specializations/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/remove/specializations/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/add/review/**").hasAuthority("STUDENT_USER")
                            .requestMatchers("/tutor/user/controller/remove/review/**").hasAuthority("ADMINISTRATOR_USER")
                            .requestMatchers("/student/user/controller/register").permitAll()
                            .requestMatchers("/student/user/controller/get/profile/**").permitAll()
                            .requestMatchers("/student/user/controller/change/password/**").hasAuthority("STUDENT_USER")
                            .anyRequest().authenticated()
                );
        return http.build();
    }
}
