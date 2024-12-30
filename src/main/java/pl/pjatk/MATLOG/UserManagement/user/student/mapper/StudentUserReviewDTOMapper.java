package pl.pjatk.MATLOG.UserManagement.user.student.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.UserManagement.user.student.dto.StudentUserReviewCreationDTO;
import pl.pjatk.MATLOG.UserManagement.user.student.dto.StudentUserReviewLookUpDTO;

@Component
public class StudentUserReviewDTOMapper {

    public StudentUserReviewLookUpDTO create(StudentUser studentUser) {
        return new StudentUserReviewLookUpDTO(studentUser.getFirstName(), studentUser.getLastName());
    }

    public StudentUserReviewCreationDTO createReviewCreationDTO(StudentUser studentUser) {
        return new StudentUserReviewCreationDTO(studentUser.getFirstName(),
                studentUser.getLastName(),
                studentUser.getEmailAddress());
    }
}
