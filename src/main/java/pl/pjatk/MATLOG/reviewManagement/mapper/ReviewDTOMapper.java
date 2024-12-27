package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;

@Component
public class ReviewDTOMapper {

    private final StudentUserReviewDTOMapper studentUserReviewDTOMapper;

    public ReviewDTOMapper(StudentUserReviewDTOMapper studentUserReviewDTOMapper) {
        this.studentUserReviewDTOMapper = studentUserReviewDTOMapper;
    }

    public ReviewDTO create(Review review) {
        return new ReviewDTO(review.getComment(),
                review.getRate(),
                review.getDateAndTimeOfComment(),
                studentUserReviewDTOMapper.create(review.getStudentUser()));
    }
}
