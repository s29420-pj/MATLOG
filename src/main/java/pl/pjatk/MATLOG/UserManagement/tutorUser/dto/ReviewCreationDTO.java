package pl.pjatk.MATLOG.userManagement.tutorUser.dto;

import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;

public record ReviewCreationDTO(Rate rate,
                                String comment,
                                LocalDateTime dateAndTimeOfReview,
                                StudentUserReviewLookUpDTO studentUser) {
}
