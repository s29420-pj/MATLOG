package pl.pjatk.MATLOG.userManagement;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pjatk.MATLOG.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{emailAddress: '?0'}")
    Optional<User> findByEmailAddress(String emailAddress);
}
