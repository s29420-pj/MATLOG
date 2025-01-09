package pl.pjatk.MATLOG.userManagement.tutorUser.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorUserRepository extends JpaRepository<TutorUserDAO, String> {

    Optional<TutorUserDAO> findByEmailAddress(String emailAddress);
}
