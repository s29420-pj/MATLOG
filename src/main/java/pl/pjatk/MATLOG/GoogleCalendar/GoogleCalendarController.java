package pl.pjatk.MATLOG.GoogleCalendar;

import com.google.api.services.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MATLOG.Domain.PrivateLesson;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.GoogleCalendar.Exceptions.GoogleCalendarException;

/**
 * Google Calendar Controller
 */
@RestController("/google/calendar/")
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;

    /**
     * Google Calendar Controller constructor
     * @param googleCalendarService Google Calendar Service
     */
    @Autowired
    public GoogleCalendarController(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    /**
     * Create calendar for user
     * @param userId User ID
     * @return ResponseEntity with created calendar
     */
    @PostMapping("create/{userId}")
    public ResponseEntity<Calendar> createCalendar(@PathVariable String userId) {
        try {
            googleCalendarService.createCalendar(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new GoogleCalendarException("Error during calendar creation");
        }
    }

    @PostMapping("create/lesson/{tutorId}/")
    public String createLessonSlotEvent(
            @PathVariable String tutorId,
            @RequestBody PrivateLesson privateLesson) {
        try {
            googleCalendarService.createLessonSlotEvent(tutorId, privateLesson);
            return "Lesson slot created successfully";
        } catch (Exception e) {
            throw new GoogleCalendarException("Error during lesson creation");
        }
    }

    @PutMapping("update/lesson/{tutorId}/")
    public String bookLessonSlotEvent(
            @PathVariable String tutorId,
            @RequestBody StudentUser studentUser,
            @RequestBody PrivateLesson privateLesson) {

        try {
            googleCalendarService.bookLessonSlotEvent(tutorId, studentUser, privateLesson);
            return "Lesson slot booked successfully";
        } catch (Exception e) {
            throw new GoogleCalendarException("Error during lesson update");
        }
    }

    @PutMapping("update/lesson/paid/{tutorId}/")
    public String paidLessonSlotEvent(
            @PathVariable String tutorId,
            @RequestBody StudentUser studentUser,
            @RequestBody PrivateLesson privateLesson) {

        try {
            googleCalendarService.paidLessonSlotEvent(tutorId, studentUser, privateLesson);
            return "Lesson slot paid successfully";
        } catch (Exception e) {
            throw new GoogleCalendarException("Error during lesson update");
        }
    }

    @PutMapping("update/lesson/cancel/{tutorId}/")
    public String cancelLessonSlotEvent(
            @PathVariable String tutorId,
            @RequestBody StudentUser studentUser,
            @RequestBody PrivateLesson privateLesson) {

        try {
            googleCalendarService.cancelLessonSlotEvent(tutorId, studentUser, privateLesson);
            return "Lesson slot cancelled successfully";
        } catch (Exception e) {
            throw new GoogleCalendarException("Error during lesson update");
        }
    }

    @GetMapping("/google/calendar/test")
    public String testCalendar() {
        try {
            Calendar calendarService = googleCalendarService.getCalendarService();
            return calendarService + "Google Calendar API client service created successfully";
        } catch (Exception e) {
            return "Error creating Google Calendar API client service";
        }
    }

}
