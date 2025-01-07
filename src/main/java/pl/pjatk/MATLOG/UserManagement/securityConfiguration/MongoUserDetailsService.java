package pl.pjatk.MATLOG.UserManagement.securityConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.*;
import pl.pjatk.MATLOG.UserManagement.studentUser.StudentUserMapperFactory;

/**
 * Component that loads user from the database
 */
@Component
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepositoryService userRepository;
    private final StudentUserMapperFactory studentUserMapperFactory;

    public MongoUserDetailsService(UserRepositoryService userRepository, StudentUserMapperFactory studentUserMapperFactory) {
        this.userRepository = userRepository;
        this.studentUserMapperFactory = studentUserMapperFactory;
    }

    /**
     * Method that loads user by username (email address) from database.
     * @param username the username (email address) identifying the user whose data is required.
     * @return SecurityUser which represents User in application
     */
    @Override
    public SecurityUser loadUserByUsername(String username) {
        User userFromDatabase = userRepository.findUserByEmailAddress(username);
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User with provided email address does not exist.");
        }
        return new SecurityUser(userFromDatabase);
    }
}
