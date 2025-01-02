package pl.pjatk.MATLOG.reviewManagement.dto;

import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.UserManagement.user.student.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;

/**
 * Record that represents Review when is displayed on Tutor's profile.
 * @param comment Comment of user.
 * @param rate Overall rate (1 - 5).
 * @param dateAndTimeOfReview Creation date and time of the review.
 * @param studentUser Students representation in Tutors profile.
 */
public record ReviewLookUpDTO(String comment,
                              Stars rate,
                              LocalDateTime dateAndTimeOfReview,
                              StudentUserReviewLookUpDTO studentUser) {
}
