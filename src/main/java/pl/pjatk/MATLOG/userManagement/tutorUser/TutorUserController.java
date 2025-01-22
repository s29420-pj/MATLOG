package pl.pjatk.MATLOG.userManagement.tutorUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewRemoveDTO;
import pl.pjatk.MATLOG.userManagement.exceptions.TutorUserNotFoundException;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.*;
import pl.pjatk.MATLOG.userManagement.user.dto.CredentialsDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.LoggedUserDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/tutor/user/controller")
@CrossOrigin(origins = "http://localhost:4200")
public class TutorUserController {

    private final TutorUserService tutorUserService;

    public TutorUserController(TutorUserService tutorUserService) {
        this.tutorUserService = tutorUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoggedUserDTO> register(@RequestBody UserRegistrationDTO userDTO) {
        if (userDTO.role().equals(Role.TUTOR)) {
            LoggedUserDTO loggedUserDTO = tutorUserService.registerUser(userDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(loggedUserDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedUserDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
        return ResponseEntity.ok(tutorUserService.login(credentialsDTO));
    }

    @GetMapping("/get/tutors")
    public ResponseEntity<List<TutorUserProfileDTO>> getAllTutors() {
        return ResponseEntity.ok(tutorUserService.getAllTutors());
    }

    @GetMapping("/get/profile/{tutorId}")
    public ResponseEntity<TutorUserProfileDTO> getTutorProfile(@PathVariable String tutorId) {
        return Stream.of(tutorUserService.getTutorUserProfile(tutorId))
                .map(ResponseEntity::ok)
                .findFirst()
                .orElseThrow(TutorUserNotFoundException::new);
    }

    @PutMapping("/change/password")
    public ResponseEntity<Void> changePassword(@RequestBody TutorUserChangePasswordDTO tutorUserChangePasswordDTO) {
        tutorUserService.changePassword(tutorUserChangePasswordDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/change/emailAddress")
    public ResponseEntity<Void> changePassword(@RequestBody TutorUserChangeEmailAddressDTO tutorUserChangeEmailAddressDTO) {
        tutorUserService.changeEmailAddress(tutorUserChangeEmailAddressDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/add/biography")
    public ResponseEntity<Void> changeBiography(@RequestBody TutorUserChangeBiographyDTO tutorUserChangeBiographyDTO) {
        tutorUserService.changeBiography(tutorUserChangeBiographyDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/add/specializations")
    public ResponseEntity<Void> addSpecialization(@RequestBody TutorUserEditSpecializationDTO tutorUserEditSpecializationDTO) {
        tutorUserService.addSpecialization(tutorUserEditSpecializationDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/remove/specializations")
    public ResponseEntity<Void> removeSpecialization(@RequestBody TutorUserEditSpecializationDTO tutorUserEditSpecializationDTO) {
        tutorUserService.removeSpecialization(tutorUserEditSpecializationDTO);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/add/review")
    public ResponseEntity<Void> addReview(@RequestBody ReviewCreationDTO reviewCreationDTO) {
        tutorUserService.addReview(reviewCreationDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/remove/review")
    public ResponseEntity<Void> removeReview(@RequestBody ReviewRemoveDTO reviewRemoveDTO) {
        tutorUserService.removeReview(reviewRemoveDTO);
        return ResponseEntity.accepted().build();
    }
}
