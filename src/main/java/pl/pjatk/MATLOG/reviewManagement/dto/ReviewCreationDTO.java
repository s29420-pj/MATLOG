package pl.pjatk.MATLOG.reviewManagement.dto;

import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;

public record ReviewCreationDTO(Rate rate,
                                String tutorId,
                                String comment,
                                LocalDateTime dateAndTimeOfReview,
                                StudentUserReviewLookUpDTO studentUser) {
}
