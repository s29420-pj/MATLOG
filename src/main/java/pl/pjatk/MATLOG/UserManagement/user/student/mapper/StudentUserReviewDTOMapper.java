package pl.pjatk.MATLOG.UserManagement.user.student.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.UserManagement.user.student.dto.StudentUserReviewDTO;

@Component
public class StudentUserReviewDTOMapper {

    public StudentUserReviewDTO create(StudentUser studentUser) {
        return new StudentUserReviewDTO(studentUser.getFirstName(), studentUser.getLastName());
    }
}
