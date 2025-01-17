package pl.pjatk.MATLOG.userManagement.studentUser.mapper;

import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

@Mapper
public class StudentUserReviewDTOMapper {

    public StudentUserReviewLookUpDTO mapToStudentReviewLookUpDTO(StudentUser studentUser) {
        return new StudentUserReviewLookUpDTO(studentUser.getId(), studentUser.getFirstName());
    }

}
