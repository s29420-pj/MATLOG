package pl.pjatk.MATLOG.userManagement;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class MongoUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepository;

    public MongoUserDetailsService(UserService userService, UserRepository repository) {
        this.userService = userService;
        this.userRepository = repository;
    }

    @Override
    public SecurityUser loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> ex = () ->
                new UsernameNotFoundException("User with that email address does not exist.");
        return new SecurityUser(userRepository.findByEmailAddress(username).orElseThrow(ex));
    }
}
