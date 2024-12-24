package pl.pjatk.MATLOG.googleCalendar;

import com.google.api.services.calendar.Calendar;
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
