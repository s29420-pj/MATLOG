package pl.pjatk.MATLOG.reviewManagement.dto;

import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;

public record ReviewDTO(String id,
                        StudentUserReviewLookUpDTO studentUserReviewLookUpDTO,
                        Rate rate,
                        String comment,
                        LocalDateTime dateAndTimeOfReview) {
}
