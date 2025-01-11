package pl.pjatk.MATLOG.userManagement.studentUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

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

    @GetMapping("/get/profile/{id}")
    public ResponseEntity<StudentUserProfileDTO> getStudentProfile(String id) {
        return ResponseEntity.ok(studentUserService.getStudentProfile(id));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StudentUserDAO> getStudentUser(@PathVariable String id) {
        return ResponseEntity.ok(studentUserService.getStudentUserDAOById(id));
    }

    @PutMapping("/change/password/{id}")
    public ResponseEntity<Void> changePassword(@PathVariable String id,
                                               @RequestParam String rawPassword) {
        studentUserService.changePassword(id, rawPassword);
        return ResponseEntity.accepted().build();
    }


}
