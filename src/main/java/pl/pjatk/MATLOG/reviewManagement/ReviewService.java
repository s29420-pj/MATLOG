package pl.pjatk.MATLOG.reviewManagement;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.Review;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getTutorUserReviews(String tutorUserId) {
        return reviewRepository.findByTutorId(tutorUserId);
    }
}
