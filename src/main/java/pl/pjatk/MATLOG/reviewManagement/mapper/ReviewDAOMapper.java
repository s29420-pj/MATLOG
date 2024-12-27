package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

@Component
public class ReviewDAOMapper {

    private final TutorUserDAOMapper tutorUserDAOMapper;
    private final StudentUserDAOMapper studentUserDAOMapper;

    public ReviewDAOMapper(TutorUserDAOMapper tutorUserDAOMapper, StudentUserDAOMapper studentUserDAOMapper) {
        this.tutorUserDAOMapper = tutorUserDAOMapper;
        this.studentUserDAOMapper = studentUserDAOMapper;
    }

    public ReviewDAO create(Review review) {
        return new ReviewDAO(
                review.getId(),
                review.getComment(),
                review.getRate(),
                review.getDateAndTimeOfComment(),
                studentUserDAOMapper.createUserDAO(review.getStudentUser()),
                tutorUserDAOMapper.createUserDAO(review.getTutorId());
    }

    public Review create(ReviewDAO reviewDAO) {
        return Review.builder()
                .withId(reviewDAO.id())
                .withComment(reviewDAO.comment())
                .withRate(reviewDAO.rate())
                .withDateAndTimeOfReview(reviewDAO.dateAndTimeOfComment())
                .withStudentId(studentUserDAOMapper.createUser(reviewDAO.student()))
                .withTutorId(tutorUserDAOMapper.createUser(reviewDAO.tutor()))
                .build();
    }
}
