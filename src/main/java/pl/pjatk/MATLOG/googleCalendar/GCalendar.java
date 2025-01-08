package pl.pjatk.MATLOG.googleCalendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * Google Calendar service wrapper
 */
@Component
@RequiredArgsConstructor
@Getter
public class GCalendar {

    private final com.google.api.services.calendar.Calendar calendarService;
}
