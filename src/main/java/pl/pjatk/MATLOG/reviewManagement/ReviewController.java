package pl.pjatk.MATLOG.reviewManagement;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;

import java.util.List;

/**
 * Review controller layer which is used by front-end to communicate with ReviewManagement application.
 */
@RestController
@RequestMapping("/review/controller")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Endpoint which returns List of ReviewLookUpDTO from provided Tutors email address.
     * @param emailAddress Email address of the Tutor user.
     * @return List of ReviewLookUpDTO
     */
    @GetMapping("/find/byTutor/{emailAddress}")
    public ResponseEntity<List<ReviewLookUpDTO>> findTutorReviewsDTO(@PathVariable String emailAddress) {
        List<ReviewLookUpDTO> tutorReviewsDTOByEmailAddress = reviewService.getTutorReviewsDTOByEmailAddress(emailAddress);
        return ResponseEntity.status(HttpStatus.OK).body(tutorReviewsDTOByEmailAddress);
    }

    /**
     * Endpoint which returns List of ReviewLookUpDTO from provided Students email address.
     * @param emailAddress Email address of the Student user.
     * @return List of ReviewLookUpDTO.
     */
    @GetMapping("/find/byStudent/{emailAddress}")
    public ResponseEntity<List<ReviewLookUpDTO>> findStudentReviewsDTO(@PathVariable String emailAddress) {
        List<ReviewLookUpDTO> studentReviewDTOByEmailAddress = reviewService.getStudentReviewsDTOByEmailAddress(emailAddress);
        return ResponseEntity.status(HttpStatus.OK).body(studentReviewDTOByEmailAddress);
    }

    /**
     * Endpoint which is used to save provided Review to the database.
     * @param reviewCreationDTO DTO representation of the Review.
     * @return ResponseEntity with CREATED Http Status.
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createReview(@RequestBody ReviewCreationDTO reviewCreationDTO) {
        reviewService.saveReview(reviewCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
