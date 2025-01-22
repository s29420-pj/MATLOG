package pl.pjatk.MATLOG.userManagement.studentUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.AuthenticationProviderService;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserChangeEmailAddressDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserChangePasswordDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.CredentialsDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.LoggedUserDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

@RestController
@RequestMapping("/student/user/controller")
public class StudentUserController {

    private final StudentUserService studentUserService;
    private final AuthenticationProviderService authenticationProviderService;

    public StudentUserController(StudentUserService studentUserService, AuthenticationProviderService authenticationProviderService) {
        this.studentUserService = studentUserService;
        this.authenticationProviderService = authenticationProviderService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoggedUserDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        if (userRegistrationDTO.role().equals(Role.STUDENT)) {
            LoggedUserDTO loggedUserDTO = studentUserService.registerUser(userRegistrationDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(loggedUserDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedUserDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
        LoggedUserDTO login = studentUserService.login(credentialsDTO);
        login.setToken(authenticationProviderService.createToken(login));
        return ResponseEntity.ok(login);
    }

    @GetMapping("/get/profile/{id}")
    public ResponseEntity<StudentUserProfileDTO> getStudentProfile(@PathVariable String id) {
        return ResponseEntity.ok(studentUserService.getStudentProfile(id));
    }

    @PutMapping("/change/password")
    public ResponseEntity<Void> changePassword(@RequestBody StudentUserChangePasswordDTO studentUserChangePasswordDTO) {
        studentUserService.changePassword(studentUserChangePasswordDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/change/emailAddress")
    public ResponseEntity<Void> changeEmailAddress(@RequestBody StudentUserChangeEmailAddressDTO studentUserChangeEmailAddressDTO) {
        studentUserService.changeEmailAddress(studentUserChangeEmailAddressDTO);
        return ResponseEntity.accepted().build();
    }
}
