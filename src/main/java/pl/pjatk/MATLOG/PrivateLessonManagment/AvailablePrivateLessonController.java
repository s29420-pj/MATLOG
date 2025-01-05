package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.AvailablePrivateLessonDTO;

import java.util.List;

@RestController
@RequestMapping("/private_lesson/available/controller")
public class AvailablePrivateLessonController {

    private final AvailablePrivateLessonService availablePrivateLessonService;

    public AvailablePrivateLessonController(AvailablePrivateLessonService availablePrivateLessonService) {
        this.availablePrivateLessonService = availablePrivateLessonService;
    }

    @GetMapping("/find/byEmailAddress/{emailAddress}")
    public ResponseEntity<List<AvailablePrivateLessonDTO>> findAvailablePrivateLessons
            (@PathVariable String emailAddress) {
        return ResponseEntity.ok(availablePrivateLessonService.getByEmailAddress(emailAddress));
    }
}
