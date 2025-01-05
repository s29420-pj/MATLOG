package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.PrivateLessonManagment.persistance.AvailablePrivateLessonDAO;

import java.util.List;

@Repository
public interface AvailablePrivateLessonRepository extends MongoRepository<AvailablePrivateLessonDAO, String> {
}
