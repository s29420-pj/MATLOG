package pl.pjatk.MATLOG.googleCalendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.googleCalendar.dto.PrivateLessonCalendarDTO;
import pl.pjatk.MATLOG.googleCalendar.dto.UserCalendarDTO;
import pl.pjatk.MATLOG.googleCalendar.mapper.DateTimeUtil;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GoogleCalendarServiceTest {

    @Mock
    private GCalendar gCalendar;

    @Mock
    private Calendar calendar;

    @Mock
    private Calendar.Events events;

    @Mock
    private Calendar.Events.Insert insert;

    @Mock
    private Calendar.Events.Update update;

    @InjectMocks
    private GoogleCalendarService googleCalendarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCalendar() throws IOException {
        Calendar.Calendars mockCalendars = mock(Calendar.Calendars.class);
        Calendar.Calendars.Insert mockInsert = mock(Calendar.Calendars.Insert.class);
        Calendar mockCalendarService = mock(Calendar.class);

        when(gCalendar.getCalendarService()).thenReturn(mockCalendarService);
        when(mockCalendarService.calendars()).thenReturn(mockCalendars);
        when(mockCalendars.insert(any(com.google.api.services.calendar.model.Calendar.class)))
                .thenReturn(mockInsert);

        UserCalendarDTO userCalendarDTO = new UserCalendarDTO("John", "Doe", UUID.randomUUID().toString());

        assertDoesNotThrow(() -> googleCalendarService.createCalendar(userCalendarDTO));

        verify(mockCalendars, times(1)).insert(argThat(calendar ->
                calendar.getSummary().equals("Kalendarz użytkownika: John Doe (" + userCalendarDTO.userId() + ")")
                        && calendar.getId().equals(userCalendarDTO.userId())));
    }

    @Test
    void deleteCalendar() throws IOException {
        Calendar.Calendars mockCalendars = mock(Calendar.Calendars.class);
        Calendar.Calendars.Delete mockDelete = mock(Calendar.Calendars.Delete.class);
        Calendar mockCalendarService = mock(Calendar.class);

        when(gCalendar.getCalendarService()).thenReturn(mockCalendarService);
        when(mockCalendarService.calendars()).thenReturn(mockCalendars);
        when(mockCalendars.delete(anyString())).thenReturn(mockDelete);

        UserCalendarDTO userCalendarDTO = new UserCalendarDTO("John", "Doe", UUID.randomUUID().toString());

        assertDoesNotThrow(() -> googleCalendarService.deleteCalendar(userCalendarDTO));

        verify(mockCalendars, times(1)).delete(userCalendarDTO.userId());
    }

    @Test
    void createEvent() throws IOException {
        String calendarId = "test-calendar-id";
        Event event = new Event().setSummary("test event");

        when(gCalendar.getCalendarService()).thenReturn(calendar);
        when(calendar.events()).thenReturn(events);
        when(events.insert(eq(calendarId), eq(event))).thenReturn(insert);

        assertDoesNotThrow(() -> googleCalendarService.createEvent(calendarId, event));

        verify(gCalendar).getCalendarService();
        verify(calendar).events();
        verify(events).insert(eq(calendarId), eq(event));
        verify(insert).execute();
    }

    @Test
    void updateEvent() throws IOException {
        String calendarId = "test-calendar-id";
        String eventId = "test-event-id";
        Event event = new Event().setId(eventId).setSummary("test event");

        when(gCalendar.getCalendarService()).thenReturn(calendar);
        when(calendar.events()).thenReturn(events);
        when(events.update(eq(calendarId), eq(eventId), eq(event))).thenReturn(update);

        assertDoesNotThrow(() -> googleCalendarService.updateEvent(calendarId, event));

        verify(gCalendar).getCalendarService();
        verify(calendar).events();
        verify(events).update(eq(calendarId), eq(eventId), eq(event));
        verify(update).execute();
    }

    @Test
    void createLessonSlotEvent() throws IOException {
        String tutorId = "tutor-id-123";
        String lessonId = "lesson-id-456";

        PrivateLessonCalendarDTO privateLessonCalendarDTO = new PrivateLessonCalendarDTO(
                lessonId,
                Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC),
                tutorId,
                "student-id-789",
                "XXX-XXX-XXX",
                PrivateLessonStatus.AVAILABLE,
                false,
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 11, 0),
                100.0
        );

        when(gCalendar.getCalendarService()).thenReturn(calendar);
        when(calendar.events()).thenReturn(events);
        when(events.insert(eq(tutorId), any(Event.class))).thenReturn(insert);

        assertDoesNotThrow(() -> googleCalendarService.createLessonSlotEvent(privateLessonCalendarDTO));

        verify(gCalendar).getCalendarService();
        verify(calendar).events();
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(events).insert(eq(tutorId), eventCaptor.capture());
        verify(insert).execute();

        Event capturedEvent = eventCaptor.getValue();
        assertEquals("WOLNE MIEJSCE", capturedEvent.getSummary());
        assertEquals(DateTimeUtil.toGoogleDateTime(LocalDateTime.of(2024, 12, 25, 10, 0)), capturedEvent.getStart().getDateTime());
        assertEquals(DateTimeUtil.toGoogleDateTime(LocalDateTime.of(2024, 12, 25, 11, 0)), capturedEvent.getEnd().getDateTime());
    }

    @Test
    void bookLessonSlotEvent() throws IOException {
        // Przygotowanie danych
        String tutorId = "test-tutor-id";
        String lessonId = "test-lesson-id";
        PrivateLessonCalendarDTO privateLessonCalendarDTO = new PrivateLessonCalendarDTO(
                lessonId,
                Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC),
                tutorId,
                "test-student-id",
                "XXX-XXX-XXX",
                PrivateLessonStatus.BOOKED,
                false,
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 11, 0),
                100.0
        );

        UserCalendarDTO userCalendarDTO = new UserCalendarDTO("John", "Doe", "user-123");

        // Mockowanie
        when(gCalendar.getCalendarService()).thenReturn(calendar);
        when(calendar.events()).thenReturn(events);

        // Mockowanie wywołania get
        Calendar.Events.Get getMock = mock(Calendar.Events.Get.class);
        when(events.get(tutorId, lessonId)).thenReturn(getMock);

        Event existingEvent = new Event().setId(lessonId).setSummary("Old Summary");
        when(getMock.execute()).thenReturn(existingEvent);

        // Mockowanie update
        Calendar.Events.Update updateMock = mock(Calendar.Events.Update.class);
        when(events.update(eq(tutorId), eq(lessonId), any(Event.class))).thenReturn(updateMock);

        // Wywołanie metody
        assertDoesNotThrow(() -> googleCalendarService.bookLessonSlotEvent(privateLessonCalendarDTO, userCalendarDTO));

        // Weryfikacja
        verify(gCalendar, times(2)).getCalendarService();
        verify(calendar, times(2)).events();
        verify(events).get(eq(tutorId), eq(lessonId));
        verify(getMock).execute();

        // Sprawdzenie aktualizacji
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(events).update(eq(tutorId), eq(lessonId), eventCaptor.capture());
        verify(updateMock).execute();

        Event updatedEvent = eventCaptor.getValue();
        assertEquals("ZAREZERWOWANE (NIEOPŁACONE) - John Doe", updatedEvent.getSummary());
        assertEquals("Start lekcji: 2024-12-25T10:00\n" +
                "Koniec lekcji: 2024-12-25T11:00\n" +
                "Kod Spotkania: XXX-XXX-XXX\n" +
                "Status: BOOKED\n" +
                "Czy lekcja może odbyć się stacjonarnie?: false\n" +
                "Cena: 100.0zł", updatedEvent.getDescription());
        assertEquals("5", updatedEvent.getColorId()); // Oczekiwany kolor dla statusu BOOKED
    }

    @Test
    void paidLessonSlotEvent() throws IOException {
        // Przygotowanie danych
        String tutorId = "test-tutor-id";
        String studentId = "test-student-id";
        String lessonId = "test-lesson-id";

        PrivateLessonCalendarDTO privateLessonCalendarDTO = new PrivateLessonCalendarDTO(
                lessonId,
                Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC),
                tutorId,
                studentId,
                "XXX-XXX-XXX",
                PrivateLessonStatus.PAID,
                true,
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 11, 0),
                150.0
        );

        UserCalendarDTO userCalendarDTO = new UserCalendarDTO("Jane", "Doe", "user-123");

        // Mockowanie serwisu kalendarza
        when(gCalendar.getCalendarService()).thenReturn(calendar);
        when(calendar.events()).thenReturn(events);

        // Mockowanie wywołania get
        Calendar.Events.Get getMock = mock(Calendar.Events.Get.class);
        when(events.get(tutorId, lessonId)).thenReturn(getMock);

        Event existingEvent = new Event().setId(lessonId).setSummary("Old Summary");
        when(getMock.execute()).thenReturn(existingEvent);

        // Mockowanie update
        Calendar.Events.Update updateMock = mock(Calendar.Events.Update.class);
        when(events.update(eq(tutorId), eq(lessonId), any(Event.class))).thenReturn(updateMock);

        // Mockowanie create
        Calendar.Events.Insert insertMock = mock(Calendar.Events.Insert.class);
        when(events.insert(eq(studentId), any(Event.class))).thenReturn(insertMock);

        // Wywołanie testowanej metody
        assertDoesNotThrow(() -> googleCalendarService.paidLessonSlotEvent(privateLessonCalendarDTO, userCalendarDTO));

        // Weryfikacja wywołań
        verify(gCalendar, times(3)).getCalendarService();
        verify(calendar, times(3)).events();
        verify(events).get(eq(tutorId), eq(lessonId));
        verify(getMock).execute();

        // Weryfikacja aktualizacji wydarzenia
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(events).update(eq(tutorId), eq(lessonId), eventCaptor.capture());
        verify(updateMock).execute();

        // Weryfikacja stworzenia wydarzenia
        verify(events).insert(eq(studentId), eventCaptor.capture());
        verify(insertMock).execute();

        // Walidacja szczegółów wydarzenia
        List<Event> capturedEvents = eventCaptor.getAllValues();
        assertEquals(2, capturedEvents.size());

        Event updatedEvent = capturedEvents.get(0);
        assertEquals("ZAREZERWOWANE (OPŁACONE) - Jane Doe", updatedEvent.getSummary());
        assertEquals("Start lekcji: 2024-12-25T10:00\n" +
                "Koniec lekcji: 2024-12-25T11:00\n" +
                "Kod Spotkania: XXX-XXX-XXX\n" +
                "Status: PAID\n" +
                "Czy lekcja może odbyć się stacjonarnie?: true\n" +
                "Cena: 150.0zł", updatedEvent.getDescription());
        assertEquals("10", updatedEvent.getColorId()); // Oczekiwany kolor dla statusu PAID

        Event createdEvent = capturedEvents.get(1);
        assertEquals(updatedEvent.getSummary(), createdEvent.getSummary());
        assertEquals(updatedEvent.getDescription(), createdEvent.getDescription());
        assertEquals(updatedEvent.getColorId(), createdEvent.getColorId());
    }

    @Test
    void cancelLessonSlotEvent() throws IOException {
        // Przygotowanie danych testowych
        String tutorId = "test-tutor-id";
        String studentId = "test-student-id";
        String lessonId = "test-lesson-id";

        PrivateLessonCalendarDTO privateLessonCalendarDTO = new PrivateLessonCalendarDTO(
                lessonId,
                Arrays.asList(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC),
                tutorId,
                studentId,
                "XXX-XXX-XXX",
                PrivateLessonStatus.CANCELLED,
                false,
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 11, 0),
                150.0
        );

        UserCalendarDTO userCalendarDTO = new UserCalendarDTO("John", "Doe", "user-123");

        // Mockowanie obiektów
        when(gCalendar.getCalendarService()).thenReturn(calendar);
        when(calendar.events()).thenReturn(events);

        // Mockowanie metody get()
        Calendar.Events.Get getMock = mock(Calendar.Events.Get.class);
        when(events.get(eq(tutorId), eq(lessonId))).thenReturn(getMock);

        Event existingEvent = new Event().setId(lessonId).setSummary("Old Summary");
        when(getMock.execute()).thenReturn(existingEvent);

        // Mockowanie metody update()
        Calendar.Events.Update updateMock = mock(Calendar.Events.Update.class);
        when(events.update(eq(tutorId), eq(lessonId), any(Event.class))).thenReturn(updateMock);
        when(events.update(eq(studentId), eq(lessonId), any(Event.class))).thenReturn(updateMock);
        when(updateMock.execute()).thenReturn(null); // symulacja braku błędów

        // Wywołanie metody do testowania
        assertDoesNotThrow(() -> googleCalendarService.cancelLessonSlotEvent(privateLessonCalendarDTO, userCalendarDTO));

        // Weryfikacja wywołań
        verify(gCalendar, times(3)).getCalendarService();
        verify(calendar, times(3)).events();
        verify(events).get(eq(tutorId), eq(lessonId));
        verify(getMock).execute();

        // Weryfikacja podwójnego wywołania metody update()
        verify(events, times(1)).update(eq(tutorId), eq(lessonId), any(Event.class));
        verify(events, times(1)).update(eq(studentId), eq(lessonId), any(Event.class));
        verify(updateMock, times(2)).execute();
    }

    @Test
    void testConnection() {

    }

    @Test
    void eventColorManager() {
        PrivateLessonStatus status = PrivateLessonStatus.AVAILABLE;
        PrivateLessonStatus status2 = PrivateLessonStatus.BOOKED;
        PrivateLessonStatus status3 = PrivateLessonStatus.PAID;
        PrivateLessonStatus status4 = PrivateLessonStatus.CANCELLED;

        assertEquals("2", googleCalendarService.eventColorManager(status));
        assertEquals("5", googleCalendarService.eventColorManager(status2));
        assertEquals("10", googleCalendarService.eventColorManager(status3));
        assertEquals("4", googleCalendarService.eventColorManager(status4));
    }

    @Test
    void buildSummary() {
        UserCalendarDTO userCalendarDTO = new UserCalendarDTO("John", "Doe", UUID.randomUUID().toString());
        PrivateLessonStatus status = PrivateLessonStatus.AVAILABLE;
        PrivateLessonStatus status2 = PrivateLessonStatus.BOOKED;
        PrivateLessonStatus status3 = PrivateLessonStatus.PAID;
        PrivateLessonStatus status4 = PrivateLessonStatus.CANCELLED;

        // Testing the method with UserCalendarDTO provided
        assertEquals("WOLNE MIEJSCE", GoogleCalendarService.buildSummary(status, userCalendarDTO));
        assertEquals("ZAREZERWOWANE (NIEOPŁACONE) - John Doe", GoogleCalendarService.buildSummary(status2, userCalendarDTO));
        assertEquals("ZAREZERWOWANE (OPŁACONE) - John Doe", GoogleCalendarService.buildSummary(status3, userCalendarDTO));
        assertEquals("ANULOWANE - John Doe", GoogleCalendarService.buildSummary(status4, userCalendarDTO));

        // Testing the method without UserCalendarDTO (only status provided)
        assertEquals("WOLNE MIEJSCE", GoogleCalendarService.buildSummary(status));
        assertEquals("NIEZNANY STATUS / NIE ZDEFINIOWANO DANYCH UŻYTKOWNIKA", GoogleCalendarService.buildSummary(status2));
        assertEquals("NIEZNANY STATUS / NIE ZDEFINIOWANO DANYCH UŻYTKOWNIKA", GoogleCalendarService.buildSummary(status3));
        assertEquals("NIEZNANY STATUS / NIE ZDEFINIOWANO DANYCH UŻYTKOWNIKA", GoogleCalendarService.buildSummary(status4));
    }

    @Test
    void buildDescription() {
        PrivateLessonCalendarDTO privateLessonCalendarDTO = new PrivateLessonCalendarDTO(
                UUID.randomUUID().toString(),
                List.of(SchoolSubject.MATHEMATICS, SchoolSubject.LOGIC),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "XXX-XXX-XXX",
                PrivateLessonStatus.AVAILABLE,
                false,
                LocalDateTime.now().plusDays(1).plusHours(12),
                LocalDateTime.now().plusDays(1).plusHours(13),
                80.0
        );

        assertEquals("Start lekcji: " + privateLessonCalendarDTO.startTime() +
                "\nKoniec lekcji: " + privateLessonCalendarDTO.endTime() +
                "\nKod Spotkania: " + privateLessonCalendarDTO.connectionCode() +
                "\nStatus: " + privateLessonCalendarDTO.status() +
                "\nCzy lekcja może odbyć się stacjonarnie?: " + privateLessonCalendarDTO.isAvailableOffline() +
                "\nCena: " + privateLessonCalendarDTO.price() + "zł", GoogleCalendarService.buildDescription(privateLessonCalendarDTO));
    }
}