package pl.pjatk.MATLOG.reviewManagement.persistance;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserDAO;

import java.time.LocalDateTime;

/**
 * Review representation in database.
 * @param id Identification of the Review.
 * @param comment Comment which is created by Student.
 * @param rate Rate of Tutor.
 * @param dateAndTimeOfComment Creation time and date of Review.
 * @param student Student who created Review.
 * @param tutor Tutor which is concerned.
 */
@Document("review")
public record ReviewDAO(@MongoId
                        String id,
                        String comment,
                        Stars rate,
                        LocalDateTime dateAndTimeOfComment,
                        @DBRef
                        StudentUserDAO student,
                        @DBRef
                        TutorUserDAO tutor) {
}
