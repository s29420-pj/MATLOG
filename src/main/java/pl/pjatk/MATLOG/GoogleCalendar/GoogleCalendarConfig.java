package pl.pjatk.MATLOG.GoogleCalendar;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleCalendarConfig {

    @Value("${google.calendar.application.name}")
    private String APPLICATION_NAME;

    @Value("${google.calendar.service.account.file}")
    private String SERVICE_ACCOUNT_FILE_PATH;
}
