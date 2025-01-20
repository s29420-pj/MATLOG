package pl.pjatk.MATLOG.privateLessonManagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonCreateDTO;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonDTO;

import java.util.List;

@RestController
@RequestMapping("/private_lesson/available/controller")
@CrossOrigin(origins = "http://localhost:4200")
public class PrivateLessonController {

    private final PrivateLessonService privateLessonService;

    public PrivateLessonController(PrivateLessonService privateLessonService) {
        this.privateLessonService = privateLessonService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPrivateLesson(@RequestBody PrivateLessonCreateDTO privateLesson) {
        privateLessonService.createPrivateLesson(privateLesson);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/{id}/{studentId}")
    public ResponseEntity<Void> bookPrivateLesson(@PathVariable String id, @PathVariable String studentId) {
        privateLessonService.bookPrivateLesson(id, studentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/paid/{id}")
    public ResponseEntity<Void> markAsPaid(@PathVariable String id) {
        privateLessonService.paidPrivateLesson(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelPrivateLesson(@PathVariable String id) {
        privateLessonService.cancelPrivateLesson(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePrivateLesson(@PathVariable String id) {
        privateLessonService.deletePrivateLesson(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPrivateLessons() {
        return ResponseEntity.ok(privateLessonService.getAllPrivateLessons());
    }

    @GetMapping("/get-all-available")
    public ResponseEntity<List<PrivateLessonDTO>> getAllAvailablePrivateLessons() {
        return ResponseEntity.ok(privateLessonService.getAllAvailablePrivateLessons());
    }

    @GetMapping("/get-all-booked")
    public ResponseEntity<List<PrivateLessonDTO>> getAllBookedPrivateLessons() {
        return ResponseEntity.ok(privateLessonService.getAllBookedPrivateLessons());
    }

    @GetMapping("/get-all-paid")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPaidPrivateLessons() {
        return ResponseEntity.ok(privateLessonService.getAllPaidPrivateLessons());
    }

    @GetMapping("/tutor-{tutorId}/get-all")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllPrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/tutor-{tutorId}/get-all-available")
    public ResponseEntity<List<PrivateLessonDTO>> getAllAvailablePrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllAvailablePrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/tutor-{tutorId}/get-all-booked")
    public ResponseEntity<List<PrivateLessonDTO>> getAllBookedPrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllBookedPrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/tutor-{tutorId}/get-all-paid")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPaidPrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllPaidPrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/student-{studentId}/get-all")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPrivateLessonsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(privateLessonService.getAllPrivateLessonsByStudentId(studentId));
    }

    @GetMapping("/student-{studentId}/get-all-booked")
    public ResponseEntity<List<PrivateLessonDTO>> getAllBookedPrivateLessonsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(privateLessonService.getAllBookedPrivateLessonsByStudentId(studentId));
    }

    @GetMapping("/student-{studentId}/get-all-paid")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPaidPrivateLessonsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(privateLessonService.getAllPaidPrivateLessonsByStudentId(studentId));
    }

}
