package pl.pjatk.MATLOG.UserManagement.user.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.Domain.TutorUser;

import java.util.Optional;

@Repository
public interface TutorUserRepository extends MongoRepository<TutorUser, String> {

    Optional<TutorUser> findByEmailAddress(String emailAddress);
}
