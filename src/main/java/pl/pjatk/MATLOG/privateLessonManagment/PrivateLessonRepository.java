package pl.pjatk.MATLOG.privateLessonManagment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.domain.PrivateLesson;

import java.util.Set;

@Repository
public interface PrivateLessonRepository extends MongoRepository<PrivateLesson, String> {
    Set<PrivateLesson> findByTutorId(String tutorId);
    Set<PrivateLesson> findByStudentId(String studentId);
}