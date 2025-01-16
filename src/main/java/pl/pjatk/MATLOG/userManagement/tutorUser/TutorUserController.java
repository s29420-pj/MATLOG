package pl.pjatk.MATLOG.userManagement.tutorUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.enums.Role;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;
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

    @GetMapping("/get/profile/{tutorId}")
    public ResponseEntity<TutorUserProfileDTO> getTutorProfile(@PathVariable String tutorId) {
        return ResponseEntity.ok(tutorUserService.getTutorUserProfile(tutorId));
    }

    @PutMapping("/change/password/{tutorId}")
    public ResponseEntity<Void> changePassword(@PathVariable String tutorId,
                                               @RequestBody String rawPassword) {
        tutorUserService.changePassword(tutorId, rawPassword);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/add/biography/{tutorId}")
    public ResponseEntity<Void> changeBiography(@PathVariable String tutorId,
                                                @RequestBody String biography) {
        tutorUserService.changeBiography(tutorId, biography);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/add/specializations/{tutorId}")
    public ResponseEntity<Void> addSpecialization(@PathVariable String tutorId,
                                                  @RequestBody Collection<SchoolSubject> specializations) {
        tutorUserService.addSpecialization(tutorId, specializations);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/remove/specializations/{tutorId}")
    public ResponseEntity<Void> removeSpecialization(@PathVariable String tutorId,
                                                     @RequestBody Collection<SchoolSubject> specializations) {
        tutorUserService.removeSpecialization(tutorId, specializations);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/add/review/{tutorId}")
    public ResponseEntity<Void> addReview(@PathVariable String tutorId,
                                          @RequestBody ReviewCreationDTO reviewCreationDTO) {
        tutorUserService.addReview(tutorId, reviewCreationDTO);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/remove/review/{tutorId}")
    public ResponseEntity<Void> removeReview(@PathVariable String tutorId,
                                             @RequestBody ReviewDTO reviewDTO) {
        tutorUserService.removeReview(tutorId, reviewDTO);
        return ResponseEntity.accepted().build();
    }
}
