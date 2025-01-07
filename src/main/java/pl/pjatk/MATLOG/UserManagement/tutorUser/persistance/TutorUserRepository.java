package pl.pjatk.MATLOG.UserManagement.tutorUser.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorUserRepository extends MongoRepository<TutorUserDAO, String> {

    Optional<TutorUserDAO> findByEmailAddress(String emailAddress);
}
