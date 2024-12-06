package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.MATLOG.UserManagement.user.mappers.RegisterUserDTOMapper;

@RestController
@RequestMapping("/user/controller")
public class UserController {

    private final UserService userService;
    private final RegisterUserDTOMapper registerUserDTOMapper;

    public UserController(UserService userService, RegisterUserDTOMapper registerUserDTOMapper) {
        this.userService = userService;
        this.registerUserDTOMapper = registerUserDTOMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.createUser(registerUserDTOMapper.mapTo(userRegistrationDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
