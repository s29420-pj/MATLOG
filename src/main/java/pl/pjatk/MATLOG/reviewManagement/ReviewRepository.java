package pl.pjatk.MATLOG.reviewManagement;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.pjatk.MATLOG.Domain.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
