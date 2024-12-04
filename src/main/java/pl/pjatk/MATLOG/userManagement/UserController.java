package pl.pjatk.MATLOG.userManagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.User;

@RestController
@RequestMapping("/user/controller")
public class UserController {

    private final UserService userService;
    private final AuthenticationProvider authProvider;

    public UserController(UserService userService, AuthenticationProvider authProvider) {
        this.userService = userService;
        this.authProvider = authProvider;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Authentication authentication) {
        authProvider.authenticate(authentication);
        return ResponseEntity.ok("Logged as " + authentication.getName());
    }
}
