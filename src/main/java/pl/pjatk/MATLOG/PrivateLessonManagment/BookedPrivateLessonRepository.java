package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.PrivateLessonManagment.persistance.BookedPrivateLessonDAO;

@Repository
public interface BookedPrivateLessonRepository extends MongoRepository<BookedPrivateLessonDAO, String> {
}
