package pl.pjatk.MATLOG.userManagement.tutorUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.ReviewDAO;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDAO, String> {
    void removeById(String id);
}
