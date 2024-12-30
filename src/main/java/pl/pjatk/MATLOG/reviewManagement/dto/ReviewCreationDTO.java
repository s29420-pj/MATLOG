package pl.pjatk.MATLOG.reviewManagement.dto;

import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.UserManagement.user.student.dto.StudentUserReviewCreationDTO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.TutorUserReviewCreationDTO;

import java.time.LocalDateTime;

public record ReviewCreationDTO(String comment,
                                Stars rate,
                                LocalDateTime dateAndTimeOfReview,
                                StudentUserReviewCreationDTO studentUser,
                                TutorUserReviewCreationDTO tutorUser) {
}
