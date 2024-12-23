package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.pjatk.MATLOG.Domain.User;

import java.util.Collection;
import java.util.Objects;

/**
 * Class that represents User in Spring Security.
 * It's used by framework to log in and register user.
 */
public final class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }
}
