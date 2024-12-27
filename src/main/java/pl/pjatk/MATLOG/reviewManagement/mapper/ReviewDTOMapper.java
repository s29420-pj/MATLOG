package pl.pjatk.MATLOG.reviewManagement.mapper;

import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;

public class ReviewDTOMapper {



    public ReviewDTO create(Review review) {
        return new ReviewDTO(review.getComment(),
                review.getRate(),
                review.getDateAndTimeOfComment(),
                review.getStudentUser())
    }
}
