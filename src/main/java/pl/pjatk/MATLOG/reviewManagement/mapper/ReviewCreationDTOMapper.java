package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;

@Component
public class ReviewCreationDTOMapper {

    public Review create(ReviewCreationDTO reviewCreationDTO, StudentUser studentUser,
                         TutorUser tutorUser) {
        return Review.builder()
                .withComment(reviewCreationDTO.comment())
                .withRate(reviewCreationDTO.rate())
                .withDateAndTimeOfReview(reviewCreationDTO.dateAndTimeOfReview())
                .withStudent(studentUser)
                .withTutor(tutorUser)
                .build();
    }
}
