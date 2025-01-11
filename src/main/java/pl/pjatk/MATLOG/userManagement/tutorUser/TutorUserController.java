package pl.pjatk.MATLOG.userManagement.tutorUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.ReviewDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.Collection;

@RestController
@RequestMapping("/tutor/user/controller")
public class TutorUserController {

    private final TutorUserService tutorUserService;

    public TutorUserController(TutorUserService tutorUserService) {
        this.tutorUserService = tutorUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO userDTO) {
        if (userDTO.role().equals(Role.TUTOR)) {
            tutorUserService.registerUser(userDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Tutor user " + userDTO.emailAddress() + " has been registered");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Tried to create " + userDTO.role() + " as TutorUser");
    }

    @GetMapping("/get/profile/{id}")
    public ResponseEntity<TutorUserProfileDTO> getTutorProfile(@PathVariable String id) {
        return ResponseEntity.ok(tutorUserService.getTutorUserProfile(id));
    }

    @PutMapping("/change/password/{id}")
    public ResponseEntity<Void> changePassword(@PathVariable String id,
                                               @RequestParam String rawPassword) {
        tutorUserService.changePassword(id, rawPassword);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/add/biography/{id}")
    public ResponseEntity<Void> changeBiography(@PathVariable String id,
                                                @RequestParam String biography) {
        tutorUserService.changeBiography(id, biography);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/add/collection/specialization/{id}")
    public ResponseEntity<Void> addSpecialization(@PathVariable String id,
                                                  @RequestBody Collection<SchoolSubject> specializations) {
        tutorUserService.addSpecialization(id, specializations);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/add/specialization/{id}")
    public ResponseEntity<Void> addSpecialization(@PathVariable String id,
                                                  @RequestBody SchoolSubject specialization) {
        tutorUserService.addSpecialization(id, specialization);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/remove/collection/specialization/{id}")
    public ResponseEntity<Void> removeSpecialization(@PathVariable String id,
                                                     @RequestBody Collection<SchoolSubject> specializations) {
        tutorUserService.removeSpecialization(id, specializations);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/remove/specialization/{id}")
    public ResponseEntity<Void> removeSpecialization(@PathVariable String id,
                                                     @RequestBody SchoolSubject specialization) {
        tutorUserService.removeSpecialization(id, specialization);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/add/review/{id}")
    public ResponseEntity<Void> addReview(@PathVariable String id,
                                          @RequestBody ReviewCreationDTO reviewCreationDTO) {
        tutorUserService.addReview(id, reviewCreationDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/remove/review/{id}")
    public ResponseEntity<Void> removeReview(@PathVariable String id,
                                             @RequestBody ReviewDTO reviewDTO) {
        tutorUserService.removeReview(id, reviewDTO);
        return ResponseEntity.accepted().build();
    }
}
