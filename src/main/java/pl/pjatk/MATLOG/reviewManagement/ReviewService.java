package pl.pjatk.MATLOG.reviewManagement;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Review;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


}
