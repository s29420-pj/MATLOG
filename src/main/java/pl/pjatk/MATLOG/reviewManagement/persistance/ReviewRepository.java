package pl.pjatk.MATLOG.reviewManagement.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDAO, String> {
    void removeById(String id);
}
