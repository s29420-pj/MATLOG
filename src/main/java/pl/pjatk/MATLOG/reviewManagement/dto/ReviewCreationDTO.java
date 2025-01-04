package pl.pjatk.MATLOG.reviewManagement.dto;

import pl.pjatk.MATLOG.Domain.Enums.Stars;
import java.time.LocalDateTime;

/**
 * Record that represents Review at the stage of creation.<br/>
 * Selected params are MUST-HAVE in order to be able to convert between DAO and DTO.
 * @param comment Comment of the user.
 * @param rate Rate from 1 to 5.
 * @param dateAndTimeOfReview Date and time of review creation.
 * @param studentUserEmailAddress Student's email address.
 * @param tutorUserEmailAddress Tutor's email address.
 */
public record ReviewCreationDTO(String comment,
                                Stars rate,
                                LocalDateTime dateAndTimeOfReview,
                                String studentUserEmailAddress,
                                String tutorUserEmailAddress) {
}
