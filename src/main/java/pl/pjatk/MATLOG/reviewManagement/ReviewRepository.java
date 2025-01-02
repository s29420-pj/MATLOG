package pl.pjatk.MATLOG.reviewManagement;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

import java.util.List;

/**
 * Review repository layer used to talk with database.
 */
@Repository
public interface ReviewRepository extends MongoRepository<ReviewDAO, String> {

    List<ReviewDAO> findAllByTutor_EmailAddress(String tutorEmailAddress);

    List<ReviewDAO> findAllByStudent_EmailAddress(String studentEmailAddress);
}
