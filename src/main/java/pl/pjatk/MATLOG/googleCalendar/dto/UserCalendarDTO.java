package pl.pjatk.MATLOG.googleCalendar.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public record UserCalendarDTO(
        String firstName,
        String lastName,
        String userId
) {}
