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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.AuthenticationProviderService;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.JWTAuthFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebAuthorizationConfiguration {

    private final AuthenticationProviderService authenticationProvider;

    public WebAuthorizationConfiguration(AuthenticationProviderService authenticationProvider) {
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
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JWTAuthFilter(authenticationProvider), BasicAuthenticationFilter.class)
                .sessionManagement(customizer ->
                        customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/tutor/user/controller/login").permitAll()
                            .requestMatchers("/tutor/user/controller/register").permitAll()
                            .requestMatchers("/student/user/controller/register").permitAll()
                            .requestMatchers("/student/user/controller/login").permitAll()
                            .requestMatchers("/tutor/user/controller/get/profile/**").permitAll()
                            .requestMatchers("/tutor/user/controller/get/tutors").permitAll()
                            .requestMatchers("/student/user/controller/get/profile/**").permitAll()
                            .requestMatchers("/tutor/user/controller/change/password").hasAuthority("TUTOR")
                            .requestMatchers("/tutor/user/controller/change/emailAddress").hasAuthority("TUTOR")
                            .requestMatchers("/tutor/user/controller/add/biography").hasAuthority("TUTOR")
                            .requestMatchers("/tutor/user/controller/add/specializations").hasAuthority("TUTOR")
                            .requestMatchers("/tutor/user/controller/remove/specializations").hasAuthority("TUTOR")
                            .requestMatchers("/tutor/user/controller/add/review").hasAuthority("STUDENT")
                            .requestMatchers("/tutor/user/controller/remove/review").hasAuthority("ADMINISTRATOR_USER")
                            .requestMatchers("/student/user/controller/change/emailAddress").hasAuthority("STUDENT")
                            .requestMatchers("/student/user/controller/change/password").hasAuthority("STUDENT")
                            .anyRequest().authenticated()
                );
        return http.build();
    }
}
