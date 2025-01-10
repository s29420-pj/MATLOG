package pl.pjatk.MATLOG.userManagement.tutorUser.persistance;

import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;

@Mapper
public class ReviewDAOMapper {

    private final StudentUserDAOMapper studentUserDAOMapper;
    private final StudentUserReviewDTOMapper studentUserReviewDTOMapper;

    public ReviewDAOMapper(StudentUserDAOMapper studentUserDAOMapper,
                           StudentUserReviewDTOMapper studentUserReviewDTOMapper) {
        this.studentUserDAOMapper = studentUserDAOMapper;
        this.studentUserReviewDTOMapper = studentUserReviewDTOMapper;
    }

    public ReviewDAO mapToDAO(Review review) {
        return new ReviewDAO(
            review.getId(),
            review.getComment(),
            review.getRate(),
            review.getDateAndTimeOfComment(),
            studentUserDAOMapper.mapToDAO(review.getStudentUser())
        );
    }

    public Review mapToDomain(ReviewDAO reviewDAO) {
        return Review.builder()
                .withId(reviewDAO.id)
                .withRate(reviewDAO.rate)
                .withComment(reviewDAO.comment)
                .withDateAndTimeOfReview(reviewDAO.dateAndTimeOfReview)
                .withStudent(studentUserReviewDTOMapper.mapToStudentReviewLookUpDTO(
                        studentUserDAOMapper.mapToDomain(reviewDAO.student)
                ))
                .build();
    }
}
