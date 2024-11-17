package pl.pjatk.MATLOG.UserManagement;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.pjatk.MATLOG.domain.User;

import java.util.Collection;
import java.util.Set;

public final class SecurityUser implements UserDetails {

    private final User user;
    private final Set<GrantedAuthority> authorities;
    private boolean isAccountNonLocked;

    public SecurityUser(User user, String password, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.password = password;
        this.authorities = authorities;
        this.isAccountNonLocked = true;
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public String getPassword() {
        return password;
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
