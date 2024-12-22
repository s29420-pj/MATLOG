package pl.pjatk.MATLOG.UserManagement.user.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentUserRepository extends MongoRepository<StudentUserDAO, String> {

    Optional<StudentUserDAO> findByEmailAddress(String emailAddress);
}
