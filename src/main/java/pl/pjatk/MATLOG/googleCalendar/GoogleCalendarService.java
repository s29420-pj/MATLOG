package pl.pjatk.MATLOG.googleCalendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.googleCalendar.dto.PrivateLessonCalendarDTO;
import pl.pjatk.MATLOG.googleCalendar.dto.UserCalendarDTO;
import pl.pjatk.MATLOG.googleCalendar.mapper.DateTimeUtil;

import java.io.IOException;

/**
 * Google Calendar service is responsible for handling all the operations related to Google Calendar API.
 * Using wrapper class GCalendar, it provides methods Google Calendar API and avoid naming conflicts.
 */
@Service
@RequiredArgsConstructor
public class GoogleCalendarService {

    @Value("${google.calendar.timezone}")
    private String timeZone;

    private final GCalendar gCalendar;

    /**
     * Creates a new calendar for user.
     * @param userCalendarDTO user's data
     * @throws IOException if there is an error while creating calendar
     */
    public void createCalendar(UserCalendarDTO userCalendarDTO) throws IOException {
        gCalendar.getCalendarService()
                .calendars()
                .insert(new Calendar()
                        .setSummary("Kalendarz użytkownika: " + userCalendarDTO.firstName() + " " + userCalendarDTO.lastName() + " (" + userCalendarDTO.userId() + ")")
                        .setId(userCalendarDTO.userId())
                        .setTimeZone(timeZone))
                .execute();
    }

    /**
     * Deletes a calendar for user.
     * @param userCalendarDTO user's data containing userId
     * @throws IOException if there is an error while deleting calendar
     */
    public void deleteCalendar(UserCalendarDTO userCalendarDTO) throws IOException {
        gCalendar.getCalendarService()
                .calendars()
                .delete(userCalendarDTO.userId())
                .execute();
    }

    public void createEvent(String calendar, Event event) throws IOException {
        gCalendar.getCalendarService()
                .events()
                .insert(calendar, event)
                .execute();
        System.out.println("Event created: " + calendar + " : " + event.getSummary());
    }

    public void updateEvent(String calendar, Event event) throws IOException {
        gCalendar.getCalendarService()
                .events()
                .update(calendar, event.getId(), event)
                .execute();
        System.out.println("Event updated: " + calendar + " : " + event.getSummary());
    }

    public void createLessonSlotEvent(PrivateLessonCalendarDTO privateLessonCalendarDTO) throws IOException {

        Event event = new Event()
                .setSummary(buildSummary(privateLessonCalendarDTO.status()))
                .setId(privateLessonCalendarDTO.id())
                .setDescription(buildDescription(privateLessonCalendarDTO))
                .setColorId(eventColorManager(privateLessonCalendarDTO.status()))
                .setStart(new EventDateTime().setDateTime(DateTimeUtil.toGoogleDateTime(privateLessonCalendarDTO.startTime())))
                .setEnd(new EventDateTime().setDateTime(DateTimeUtil.toGoogleDateTime(privateLessonCalendarDTO.endTime())));

        createEvent(privateLessonCalendarDTO.tutorId(), event);
    }

    public void bookLessonSlotEvent(PrivateLessonCalendarDTO privateLessonCalendarDTO, UserCalendarDTO userCalendarDTO) throws IOException {

        Event event = gCalendar.getCalendarService().events().get(privateLessonCalendarDTO.tutorId(), privateLessonCalendarDTO.id()).execute();

        event.setSummary(buildSummary(privateLessonCalendarDTO.status(), userCalendarDTO))
                .setDescription(buildDescription(privateLessonCalendarDTO))
                .setColorId(eventColorManager(privateLessonCalendarDTO.status()));

        updateEvent(privateLessonCalendarDTO.tutorId(), event);
    }

    public void paidLessonSlotEvent(PrivateLessonCalendarDTO privateLessonCalendarDTO, UserCalendarDTO userCalendarDTO) throws IOException {

        Event event = gCalendar.getCalendarService().events().get(privateLessonCalendarDTO.tutorId(), privateLessonCalendarDTO.id()).execute();

        event.setSummary(buildSummary(privateLessonCalendarDTO.status(), userCalendarDTO))
                .setDescription(buildDescription(privateLessonCalendarDTO))
                .setColorId(eventColorManager(privateLessonCalendarDTO.status()));

        updateEvent(privateLessonCalendarDTO.tutorId(), event);
        createEvent(privateLessonCalendarDTO.studentId(), event);
    }

    public void cancelLessonSlotEvent(PrivateLessonCalendarDTO privateLessonCalendarDTO, UserCalendarDTO userCalendarDTO) throws IOException {

        Event event = gCalendar.getCalendarService().events().get(privateLessonCalendarDTO.tutorId(), privateLessonCalendarDTO.id()).execute();

        event.setSummary(buildSummary(privateLessonCalendarDTO.status(), userCalendarDTO))
                .setDescription(buildDescription(privateLessonCalendarDTO))
                .setColorId(eventColorManager(privateLessonCalendarDTO.status()));

        updateEvent(privateLessonCalendarDTO.tutorId(), event);
        updateEvent(privateLessonCalendarDTO.studentId(), event);
    }

//    public void testConnection() {
//        try {
//            gCalendar.getCalendarService().calendarList().list().execute();
//        } catch (Exception e) {
//            throw new RuntimeException("Error while getting calendar list", e);
//        }
//    }

    /** Set Event color based on PrivateLessonStatus
     * @param status status of the lesson
     * @return color id
     * */
     protected String eventColorManager(PrivateLessonStatus status) {
        return switch (status) {
            case AVAILABLE -> "2"; // sage
            case BOOKED -> "5"; // banana
            case PAID -> "10"; // basil
            case CANCELLED -> "4"; // flamingo
        };
    }

    /** Build event summary based on PrivateLessonStatus
     * @param status of the lesson
     * @param userCalendarDTO user data
     * @return event summary
     * */
    protected static String buildSummary(PrivateLessonStatus status, UserCalendarDTO userCalendarDTO) {
        return switch (status) {
            case AVAILABLE -> "WOLNE MIEJSCE";
            case BOOKED -> "ZAREZERWOWANE (NIEOPŁACONE) - " + userCalendarDTO.firstName() + " " + userCalendarDTO.lastName();
            case PAID -> "ZAREZERWOWANE (OPŁACONE) - " + userCalendarDTO.firstName() + " " + userCalendarDTO.lastName();
            case CANCELLED -> "ANULOWANE - " + userCalendarDTO.firstName() + " " + userCalendarDTO.lastName();
        };
    }

    protected static String buildSummary(PrivateLessonStatus status) {
        return switch (status) {
            case AVAILABLE -> "WOLNE MIEJSCE";
            default -> "NIEZNANY STATUS / NIE ZDEFINIOWANO DANYCH UŻYTKOWNIKA";
        };
    }

    /** Build event description based on PrivateLesson
     * @param privateLessonCalendarDTO private lesson data
     * @return event description
     * */
    protected static String buildDescription(PrivateLessonCalendarDTO privateLessonCalendarDTO) {
        return "Start lekcji: " + privateLessonCalendarDTO.startTime() +
                "\nKoniec lekcji: " + privateLessonCalendarDTO.endTime() +
                "\nKod Spotkania: " + privateLessonCalendarDTO.connectionCode() +
                "\nStatus: " + privateLessonCalendarDTO.status() +
                "\nCzy lekcja może odbyć się stacjonarnie?: " + privateLessonCalendarDTO.isAvailableOffline() +
                "\nCena: " + privateLessonCalendarDTO.price() + "zł";
    }

}
