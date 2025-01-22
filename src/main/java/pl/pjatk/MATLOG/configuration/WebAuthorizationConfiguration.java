package pl.pjatk.MATLOG.configuration;

import io.netty.handler.codec.http.HttpMethod;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebAuthorizationConfiguration {

    private final AuthenticationProvider authenticationProvider;

    public WebAuthorizationConfiguration(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        config.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
        ));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/tutor/user/controller/register").permitAll()
                            .requestMatchers("/student/user/controller/register").permitAll()
                            .requestMatchers("/tutor/user/controller/get/profile/**").permitAll()
                            .requestMatchers("/tutor/user/controller/get/tutors").permitAll()
                            .requestMatchers("/student/user/controller/get/profile/**").permitAll()
                            .requestMatchers("/tutor/user/controller/change/password/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/add/biography/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/add/specializations/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/remove/specializations/**").hasAuthority("TUTOR_USER")
                            .requestMatchers("/tutor/user/controller/add/review/**").hasAuthority("STUDENT_USER")
                            .requestMatchers("/tutor/user/controller/remove/review/**").hasAuthority("ADMINISTRATOR_USER")
                            .requestMatchers("/student/user/controller/change/password/**").hasAuthority("STUDENT_USER")
                            .anyRequest().authenticated()
                );
        return http.build();
    }
}
