package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.PrivateLessonManagment.persistance.PrivateLessonDAO;

import java.util.List;

@Repository
public interface PrivateLessonRepository extends JpaRepository<PrivateLessonDAO, String> {
    List<PrivateLessonDAO> findAllByTutor_Id(String tutorId);
}