package pl.pjatk.MATLOG.GoogleCalendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.Domain.PrivateLesson;
import pl.pjatk.MATLOG.Domain.StudentUser;

import java.io.FileInputStream;
import java.util.Collections;

@Service
public class GoogleCalendarService {

    private static final GoogleCalendarConfig googleCalendarConfig = new GoogleCalendarConfig();

    /** Create Google Calendar API client service */
    public static Calendar getCalendarService() throws Exception {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(googleCalendarConfig.getSERVICE_ACCOUNT_FILE_PATH()))
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

        return new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName(googleCalendarConfig.getAPPLICATION_NAME())
                .build();
    }

    /** Create individual calendar for tutor or student
     * @param userId tutor or student id needed to create calendar
     * @throws Exception if calendar creation fails
     **/
    public void createCalendar(String userId) throws Exception {
        Calendar calendarService = getCalendarService();

        calendarService.calendars().insert(new com.google.api.services.calendar.model.Calendar()
                .setSummary("Kalendarz Użytkownika - " + userId)
                .setTimeZone("Europe/Warsaw"))
                .execute();
    }

    /** Create event in Google Calendar for lesson slot
     * @param tutorId tutor id needed to create event in tutor's calendar
     * @param privateLesson to display in event as description
     * @throws Exception if event creation fails
     * */
    public void createLessonSlotEvent(String tutorId, PrivateLesson privateLesson) throws Exception{
        Event event = new Event()
                .setSummary(buildSummary(privateLesson.getStatus(), null))
                .setColorId(EventColorManager(privateLesson.getStatus()))
                .setId(privateLesson.getId())
                .setDescription("Start lekcji: " + privateLesson.getStartTime() +
                                "\nKoniec lekcji: " + privateLesson.getEndTime() +
                                "\nKod Spotkania: " + privateLesson.getConnectionCode() +
                                "\nStatus: " + privateLesson.getStatus() +
                                "\nCzy lekcja może odbyć się stacjonarnie?: " + privateLesson.isAvailableOffline() +
                                "\nCena: " + privateLesson.getPrice() + "zł");

        createEvent(tutorId, event);
    }

    /**
     * Book lesson slot event in Google Calendar
     * @param tutorId tutor id which created the lesson
     * @param studentUser student user which is booking the lesson
     * @param privateLesson private lesson which is booked
     * @throws Exception if event creation fails
     */
    public void bookLessonSlotEvent(String tutorId, StudentUser studentUser, PrivateLesson privateLesson) throws Exception{
        Calendar calendarService = getCalendarService();
        Event event = calendarService.events().get(tutorId, privateLesson.getId()).execute();

        event.setSummary(buildSummary(privateLesson.getStatus(), studentUser))
                .setColorId(EventColorManager(privateLesson.getStatus()))
                .setDescription(buildDescription(privateLesson));

        updateEvent(tutorId, event);
    }

    /**
     * Change lesson slot event in Google Calendar to PAID status and create event in student's calendar
     * @param tutorId tutor id which created the lesson
     * @param studentUser student user which is booking the lesson and paying for it
     * @param privateLesson private lesson which is booked
     * @throws Exception if event creation fails
     */
    public void paidLessonSlotEvent(String tutorId, StudentUser studentUser, PrivateLesson privateLesson) throws Exception{
        Calendar calendarService = getCalendarService();
        Event event = calendarService.events().get(tutorId, privateLesson.getId()).execute();

        event.setSummary(buildSummary(privateLesson.getStatus(), studentUser))
                .setColorId(EventColorManager(privateLesson.getStatus()))
                .setDescription(buildDescription(privateLesson));

        updateEvent(tutorId, event);
        createEvent(studentUser.getId(), event);
    }

    public void cancelLessonSlotEvent(String tutorId, StudentUser studentUser, PrivateLesson privateLesson) throws Exception{
        Calendar calendarService = getCalendarService();
        Event event = calendarService.events().get(tutorId, privateLesson.getId()).execute();

        event.setSummary(buildSummary(privateLesson.getStatus(), studentUser))
                .setColorId(EventColorManager(privateLesson.getStatus()))
                .setDescription(buildDescription(privateLesson));

        updateEvent(tutorId, event);
        updateEvent(studentUser.getId(), event);
    }

    /** Create event in Google Calendar
     * @param calendar calendar used to create event, every user has its own calendar
     * @param event event to create
     * @throws Exception if event creation fails
     * */
    private void createEvent(String calendar, Event event) throws Exception {
        Calendar calendarService = getCalendarService();
        calendarService.events().insert(calendar, event).execute();
        System.out.println("Event created in user calendar" + calendar + ": " + event.getSummary());
    }

    /** Update event in Google Calendar
     * @param calendar calendar used to update event, every user has its own calendar
     * @param event event to update
     * @throws Exception if event update fails
     * */
    private void updateEvent(String calendar, Event event) throws Exception {
        Calendar calendarService = getCalendarService();
        calendarService.events().update(calendar, event.getId(), event).execute();
        System.out.println("Event updated in user calendar" + calendar + ": " + event.getSummary());
    }

    /** Set Event color based on PrivateLessonStatus
     * @param status status of the lesson
     * @return color id
     * */
    private String EventColorManager(PrivateLessonStatus status) {
        return switch (status) {
            case AVAILABLE -> "2"; // sage
            case BOOKED -> "5"; // banana
            case PAID -> "10"; // basil
            case CANCELLED -> "4"; // flamingo
        };
    }

    /** Build event summary based on PrivateLessonStatus
     * @param status status of the lesson
     * @param studentUser student user to display in summary
     * @return event summary
     * */
    private static String buildSummary(PrivateLessonStatus status, StudentUser studentUser) {
        return switch (status) {
            case AVAILABLE -> "WOLNE MIEJSCE";
            case BOOKED -> "ZAREZERWOWANE (NIEOPŁACONE) - " + studentUser.getFullName();
            case PAID -> "ZAREZERWOWANE (OPŁACONE) - " + studentUser.getFullName();
            case CANCELLED -> "ANULOWANE - " + studentUser.getFullName();
        };
    }

    /** Build event description based on PrivateLesson
     * @param privateLesson private lesson to display in description
     * @return event description
     * */
    private static String buildDescription(PrivateLesson privateLesson) {
        return "Start lekcji: " + privateLesson.getStartTime() +
                "\nKoniec lekcji: " + privateLesson.getEndTime() +
                "\nKod Spotkania: " + privateLesson.getConnectionCode() +
                "\nStatus: " + privateLesson.getStatus() +
                "\nCzy lekcja może odbyć się stacjonarnie?: " + privateLesson.isAvailableOffline() +
                "\nCena: " + privateLesson.getPrice() + "zł";
    }

}
