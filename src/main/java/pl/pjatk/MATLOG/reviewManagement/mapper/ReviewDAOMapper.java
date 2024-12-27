package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

@Component
public class ReviewDAOMapper {

    public ReviewDAO create(Review review, StudentUserDAO studentUser, TutorUserDAO tutorUser) {
        return new ReviewDAO(
                review.getId(),
                review.getComment(),
                review.getRate(),
                review.getDateAndTimeOfComment(),
                studentUser,
                tutorUser);
    }

    public Review create(ReviewDAO reviewDAO) {
        return Review.builder()
                .withId(reviewDAO.id())
                .withComment(reviewDAO.comment())
                .withRate(reviewDAO.rate())
                .withDateAndTimeOfReview(reviewDAO.dateAndTimeOfComment())
                .withStudentId(reviewDAO.student().id())
                .withTutorId(reviewDAO.tutor().id())
                .build();
    }
}
