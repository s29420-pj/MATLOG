package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.Domain.PrivateLesson;

import java.util.List;

@Repository
public interface PrivateLessonRepository extends MongoRepository<PrivateLesson, String> {
    List<PrivateLesson> findByTutorId(String tutorId);
    List<PrivateLesson> findByStudentId(String studentId);
}