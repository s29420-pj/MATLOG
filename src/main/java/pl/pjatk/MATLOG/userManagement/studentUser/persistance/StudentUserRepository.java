package pl.pjatk.MATLOG.userManagement.studentUser.persistance;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentUserRepository extends JpaRepository<StudentUserDAO, String> {

    Optional<StudentUserDAO> findByEmailAddress(String emailAddress);
}
