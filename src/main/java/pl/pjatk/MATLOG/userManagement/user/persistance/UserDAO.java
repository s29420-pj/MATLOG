package pl.pjatk.MATLOG.UserManagement.user.persistance;

import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Set;

public interface UserDAO {
    String id();
    String firstName();
    String lastName();
    String emailAddress();
    String password();
    LocalDate dateOfBirth();
    Set<GrantedAuthority> authorities();
    Boolean isAccountNonLocked();
}
