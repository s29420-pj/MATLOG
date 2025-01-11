package pl.pjatk.MATLOG.userManagement.studentUser.mapper;

import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.PrivateLessonStudentUserDTO;

@Mapper
public class StudentUserPrivateLessonDTOMapper {

    public PrivateLessonStudentUserDTO mapToDTO(StudentUser studentUser) {
        return new PrivateLessonStudentUserDTO(studentUser.getId());
    }
}
