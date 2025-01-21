package pl.pjatk.MATLOG.userManagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.MATLOG.userManagement.user.SecurityUser;

@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<SecurityUser> login(@RequestBody CredentialsDTO credentialsDTO) {
        return ResponseEntity.ok(userService.login(credentialsDTO));
    }
}
