package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;

/**
 * Mapper class that is responsible for </br>
 * converting Review into ReviewLookUpDTO.
 */
@Component
public class ReviewLookUpDTOMapper {

    private final StudentUserReviewDTOMapper studentUserReviewDTOMapper;

    public ReviewLookUpDTOMapper(StudentUserReviewDTOMapper studentUserReviewDTOMapper) {
        this.studentUserReviewDTOMapper = studentUserReviewDTOMapper;
    }

    /**
     * Method which returns ReviewLookUpDTO from provided Review.
     * @param review Review representation from domain.
     * @return ReviewLookUpDTO which is used to display information of the </br>
     * student user in tutors profile
     */
    public ReviewLookUpDTO create(Review review) {
        return new ReviewLookUpDTO(review.getComment(),
                review.getRate(),
                review.getDateAndTimeOfComment(),
                studentUserReviewDTOMapper.createStudentReviewLookUpDTO(review.getStudentUser()));
    }
}
