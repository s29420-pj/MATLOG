package pl.pjatk.MATLOG.googleCalendar.mapper;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static com.google.api.client.util.DateTime toGoogleDateTime(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZonedDateTime.now().getZone());
        String rfc3339FormattedDate = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new com.google.api.client.util.DateTime(rfc3339FormattedDate);
    }
}
