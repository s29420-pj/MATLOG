package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.PrivateLessonManagment.persistance.PrivateLessonDAO;

@Repository
public interface PrivateLessonRepository extends JpaRepository<PrivateLessonDAO, String> {
}