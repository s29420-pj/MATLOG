package pl.pjatk.MATLOG.privateLessonManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.privateLessonManagement.persistance.PrivateLessonDAO;

import java.util.List;

@Repository
public interface PrivateLessonRepository extends JpaRepository<PrivateLessonDAO, String> {
    List<PrivateLessonDAO> findAllByTutor_Id(String tutorId);
    List<PrivateLessonDAO> findAllByStudent_Id(String studentId);
}