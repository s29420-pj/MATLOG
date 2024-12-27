package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

@Component
public class ReviewDAOMapper {

    public ReviewDAO create(Review review) {
        return new ReviewDAO(
                review.getId(),
                review.getComment(),
                review.getRate(),
                review.getDateAndTimeOfComment(),
                new ObjectId(review.getStudentId()),
                new ObjectId(review.getTutorId()));
    }

    public Review create(ReviewDAO reviewDAO) {
        return Review.builder()
                .withId(reviewDAO.id())
                .withComment(reviewDAO.comment())
                .withRate(reviewDAO.rate())
                .withDateAndTimeOfReview(reviewDAO.dateAndTimeOfComment())
                .withStudentId(reviewDAO.studentId().toString())
                .withTutorId(reviewDAO.tutorId().toString())
                .build();
    }
}
