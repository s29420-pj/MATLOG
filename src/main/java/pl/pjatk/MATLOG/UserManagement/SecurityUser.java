package pl.pjatk.MATLOG.UserManagement;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.pjatk.MATLOG.domain.User;

import java.util.Collection;
import java.util.Set;

/**
 * Class that represents User in Spring Security.
 * It's used by framework to log in and register user.
 */
public final class SecurityUser implements UserDetails {

    private final User user;
    private final Set<GrantedAuthority> authorities;
    private boolean isAccountNonLocked;

    public SecurityUser(User user, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
        this.isAccountNonLocked = true;
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
        return Set.copyOf(authorities);
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }
}
