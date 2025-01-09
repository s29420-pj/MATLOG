package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.PrivateLessonDTO;

import java.util.List;

@RestController
@RequestMapping("/private_lesson/available/controller")
public class PrivateLessonController {

    private final PrivateLessonService privateLessonService;

    public PrivateLessonController(PrivateLessonService privateLessonService) {
        this.privateLessonService = privateLessonService;
    }

    @GetMapping("/find/byTutorId/{id}")
    public ResponseEntity<List<PrivateLessonDTO>> findAvailablePrivateLessons
            (@PathVariable String id) {
        return ResponseEntity.ok(privateLessonService.getPrivateLessonsByTutorId(id));
    }
}
