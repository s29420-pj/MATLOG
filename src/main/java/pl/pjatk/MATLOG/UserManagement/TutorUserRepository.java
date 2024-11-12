package pl.pjatk.MATLOG.UserManagement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.domain.TutorUser;

@Repository
public interface TutorUserRepository extends CrudRepository<TutorUser, String> {
}
