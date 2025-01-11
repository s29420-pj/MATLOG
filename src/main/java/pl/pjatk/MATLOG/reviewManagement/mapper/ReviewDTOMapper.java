package pl.pjatk.MATLOG.reviewManagement.mapper;

import pl.pjatk.MATLOG.domain.Review;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;

@Mapper
public class ReviewDTOMapper {

    public ReviewDTO mapToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getStudentUser(),
                review.getRate(),
                review.getComment(),
                review.getDateAndTimeOfComment()
        );
    }

    public Review mapToDomain(ReviewDTO reviewDTO) {
        return Review.builder()
                .withId(reviewDTO.id())
                .withRate(reviewDTO.rate())
                .withComment(reviewDTO.comment())
                .withDateAndTimeOfReview(reviewDTO.dateAndTimeOfReview())
                .withStudent(reviewDTO.studentUserReviewLookUpDTO())
                .build();
    }

    public Review mapToDomain(ReviewCreationDTO reviewCreationDTO) {
        return Review.builder()
                .withRate(reviewCreationDTO.rate())
                .withComment(reviewCreationDTO.comment())
                .withDateAndTimeOfReview(reviewCreationDTO.dateAndTimeOfReview())
                .withStudent(reviewCreationDTO.studentUser())
                .build();
    }
}
