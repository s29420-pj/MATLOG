package pl.pjatk.MATLOG.googleCalendar.mapper;

import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.googleCalendar.dto.UserCalendarDTO;

public class UserCalendarMapper {

    public UserCalendarDTO toUserCalendarDTO(User user) {
        return new UserCalendarDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }
}
