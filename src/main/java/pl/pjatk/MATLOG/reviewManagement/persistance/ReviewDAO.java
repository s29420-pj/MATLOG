package pl.pjatk.MATLOG.reviewManagement.persistance;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.*;
import pl.pjatk.MATLOG.Domain.Enums.Stars;
import pl.pjatk.MATLOG.UserManagement.user.student.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.persistance.TutorUserDAO;

import java.time.LocalDateTime;

@Document("review")
public record ReviewDAO(@MongoId String id,
                        String comment,
                        Stars rate,
                        LocalDateTime dateAndTimeOfComment,
                        @DocumentReference()
                        ObjectId studentId,
                        ObjectId tutorId) {
}
