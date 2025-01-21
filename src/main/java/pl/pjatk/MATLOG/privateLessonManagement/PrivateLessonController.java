package pl.pjatk.MATLOG.privateLessonManagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.domain.PrivateLesson;
import pl.pjatk.MATLOG.privateLessonManagement.dto.*;

import java.util.List;

@RestController
@RequestMapping("/private_lesson/")
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

    @PutMapping("/book")
    public ResponseEntity<Void> bookPrivateLesson(@RequestBody PrivateLessonBookDTO privateLessonBookDTO) {
        privateLessonService.bookPrivateLesson(privateLessonBookDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/paid")
    public ResponseEntity<Void> markAsPaid(@RequestBody PrivateLessonPaidDTO privateLessonPaidDTO) {
        privateLessonService.paidPrivateLesson(privateLessonPaidDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel")
    public ResponseEntity<Void> cancelPrivateLesson(@RequestBody PrivateLessonCancelDTO privateLessonCancelDTO) {
        privateLessonService.cancelPrivateLesson(privateLessonCancelDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePrivateLesson(@PathVariable PrivateLessonDeleteDTO privateLessonDeleteDTO) {
        privateLessonService.deletePrivateLesson(privateLessonDeleteDTO);
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

    @GetMapping("/tutor/get-all/{tutorId}")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllPrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/tutor/get-all-available/{tutorId}")
    public ResponseEntity<List<PrivateLessonDTO>> getAllAvailablePrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllAvailablePrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/tutor/get-all-booked/{tutorId}")
    public ResponseEntity<List<PrivateLessonDTO>> getAllBookedPrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllBookedPrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/tutor/get-all-paid/{tutorId}")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPaidPrivateLessonsByTutorId(@PathVariable String tutorId) {
        return ResponseEntity.ok(privateLessonService.getAllPaidPrivateLessonsByTutorId(tutorId));
    }

    @GetMapping("/student/get-all/{studentId}")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPrivateLessonsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(privateLessonService.getAllPrivateLessonsByStudentId(studentId));
    }

    @GetMapping("/student/get-all-booked/{studentId}")
    public ResponseEntity<List<PrivateLessonDTO>> getAllBookedPrivateLessonsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(privateLessonService.getAllBookedPrivateLessonsByStudentId(studentId));
    }

    @GetMapping("/student/get-all-paid/{studentId}")
    public ResponseEntity<List<PrivateLessonDTO>> getAllPaidPrivateLessonsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(privateLessonService.getAllPaidPrivateLessonsByStudentId(studentId));
    }

}
