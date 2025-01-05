package pl.pjatk.MATLOG.googleCalendar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.googleCalendar.dto.UserCalendarDTO;

import java.io.IOException;

@RestController("/api/v1/google-calendar")
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;

    public GoogleCalendarController(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    @PostMapping("/create-calendar")
    public ResponseEntity<?> createCalendar(@RequestBody UserCalendarDTO userCalendarDTO) {
        try {
            googleCalendarService.createCalendar(userCalendarDTO);
            return ResponseEntity.ok("Calendar created successfully for user with id: " + userCalendarDTO.userId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error while creating calendar: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-calendar/{userId}")
    public ResponseEntity<?> deleteCalendar(@RequestBody UserCalendarDTO userCalendarDTO) {
        try {
            googleCalendarService.deleteCalendar(userCalendarDTO);
            return ResponseEntity.ok("Calendar deleted successfully for user with id: " + userCalendarDTO.userId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error while deleting calendar: " + e.getMessage());
        }
    }

//    @GetMapping("/test-google-calendar")
//    public String testGoogleCalendar() {
//        try {
//            googleCalendarService.testConnection();
//            return "Połączenie działa! Sprawdź logi aplikacji dla listy kalendarzy.";
//        } catch (Exception e) {
//            return "Połączenie nie działa: " + e.getMessage();
//        }
//    }
}