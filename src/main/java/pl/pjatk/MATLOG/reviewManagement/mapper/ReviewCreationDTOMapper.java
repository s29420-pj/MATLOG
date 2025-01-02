package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;

/**
 * Mapper class which is responsible for <br></>
 * converting ReviewCreationDTO into Review
 */
@Component
public class ReviewCreationDTOMapper {

    /**
     * Method which return Review from provided DTO
     * @param reviewCreationDTO creation DTO of Review
     * @param studentUser student user representation from domain.
     * @param tutorUser tutor user representation from domain.
     * @return Review
     */
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
