package pl.pjatk.MATLOG.UserManagement.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserRegistrationDTO;

@RestController
@RequestMapping("/student/user/controller")
public class StudentUserController {

    private final StudentUserService studentUserService;

    public StudentUserController(StudentUserService studentUserService) {
        this.studentUserService = studentUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        if (userRegistrationDTO.role().equals(Role.STUDENT)) {
            studentUserService.registerUser(userRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Student user " + userRegistrationDTO.emailAddress() + " has been registered");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Tried to create " + userRegistrationDTO.role() + " as StudentUser");
    }
}
