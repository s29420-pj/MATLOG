package pl.pjatk.MATLOG.googleCalendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@Configuration
public class GoogleCalendarConfig {

    @Value("${google.calendar.credentials.path}")
    private String PATH_TO_CREDENTIALS;

    @Value("${google.application.name}")
    private String APPLICATION_NAME;

    private GoogleCredential googleCredential() throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(PATH_TO_CREDENTIALS);

        if (resourceAsStream == null) {
            throw new IOException("Resource not found: " + PATH_TO_CREDENTIALS);
        }

        return GoogleCredential.fromStream(resourceAsStream)
                .createScoped(Arrays.asList(CalendarScopes.CALENDAR, "https://www.googleapis.com/auth/calendar.events"));
    }

    @Bean
    public Calendar getCalendarService() throws IOException, GeneralSecurityException {
        GoogleCredential googleCredential = googleCredential();

        return new Calendar.Builder(
                googleCredential.getTransport(),
                googleCredential.getJsonFactory(),
                googleCredential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
