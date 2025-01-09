package pl.pjatk.MATLOG.userManagement.tutorUser.mapper;

import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.ReviewDTO;

@Mapper
public class ReviewDTOMapper {

    private final StudentUserReviewDTOMapper studentUserReviewLookUpDTOMapper;

    public ReviewDTOMapper(StudentUserReviewDTOMapper studentUserReviewLookUpDTOMapper) {
        this.studentUserReviewLookUpDTOMapper = studentUserReviewLookUpDTOMapper;
    }

    public ReviewDTO mapToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                studentUserReviewLookUpDTOMapper.createStudentReviewLookUpDTO(review.getStudentUser()),
                review.getRate(),
                review.getComment(),
                review.getDateAndTimeOfComment()
        );
    }
}
