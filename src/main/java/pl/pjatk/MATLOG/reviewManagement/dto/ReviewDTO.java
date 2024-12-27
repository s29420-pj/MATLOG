package pl.pjatk.MATLOG.reviewManagement.dto;

import pl.pjatk.MATLOG.Domain.Enums.Stars;

import java.time.LocalDateTime;

public record ReviewDTO(String comment,
                        Stars rate,
                        LocalDateTime dateAndTimeOfReview,
                        StudentUserReviewDTO studentUser) {
}
