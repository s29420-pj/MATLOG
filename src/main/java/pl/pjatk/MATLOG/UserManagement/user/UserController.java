package pl.pjatk.MATLOG.UserManagement.user;

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
    private final UserDTOMapper userDTOMapper;
    private final AuthenticationProvider authProvider;

    public UserController(UserService userService, UserDTOMapper userDTOMapper, AuthenticationProvider authProvider) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
        this.authProvider = authProvider;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.createUser(userDTOMapper.mapTo(userRegisterDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
