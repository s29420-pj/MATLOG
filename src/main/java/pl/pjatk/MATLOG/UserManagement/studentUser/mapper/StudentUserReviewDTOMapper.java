package pl.pjatk.MATLOG.UserManagement.studentUser.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.UserManagement.studentUser.dto.StudentUserReviewLookUpDTO;

@Component
public class StudentUserReviewDTOMapper {

    public StudentUserReviewLookUpDTO createStudentReviewLookUpDTO(StudentUser studentUser) {
        return new StudentUserReviewLookUpDTO(studentUser.getFirstName(), studentUser.getLastName());
    }


}
