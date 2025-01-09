package pl.pjatk.MATLOG.PrivateLessonManagment.dto;

import pl.pjatk.MATLOG.Domain.PrivateLesson;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserPrivateLessonDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserPrivateLessonDTOMapper;

@Mapper
public class PrivateLessonDTOMapper {

    private final StudentUserPrivateLessonDTOMapper studentUserPrivateLessonDTOMapper;
    private final TutorUserPrivateLessonDTOMapper tutorUserPrivateLessonDTOMapper;

    public PrivateLessonDTOMapper(StudentUserPrivateLessonDTOMapper studentUserPrivateLessonDTOMapper, TutorUserPrivateLessonDTOMapper tutorUserPrivateLessonDTOMapper) {
        this.studentUserPrivateLessonDTOMapper = studentUserPrivateLessonDTOMapper;
        this.tutorUserPrivateLessonDTOMapper = tutorUserPrivateLessonDTOMapper;
    }

    public PrivateLessonDTO mapToDTO(PrivateLesson privateLesson) {
        return new PrivateLessonDTO(
            privateLesson.getId(),
            tutorUserPrivateLessonDTOMapper.mapToDTO(privateLesson.getTutor()),
            studentUserPrivateLessonDTOMapper.mapToDTO(privateLesson.getStudent()),
            privateLesson.isAvailableOffline(),
            privateLesson.getStartTime(),
            privateLesson.getEndTime(),
            privateLesson.getPrice()
        );
    }
}
