package pl.pjatk.MATLOG.userManagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/controller")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationProvider authProvider;

    public UserController(UserService userService, UserMapper userMapper, AuthenticationProvider authProvider) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authProvider = authProvider;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userMapper.mapUserDtoToDomainUser(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
