package pl.pjatk.MATLOG.privateLessonManagement.dto;

import pl.pjatk.MATLOG.domain.PrivateLesson;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserPrivateLessonDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserPrivateLessonDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;

@Mapper
public class PrivateLessonDTOMapper {

    private final StudentUserPrivateLessonDTOMapper studentUserPrivateLessonDTOMapper;
    private final TutorUserPrivateLessonDTOMapper tutorUserPrivateLessonDTOMapper;

    public PrivateLessonDTOMapper(StudentUserPrivateLessonDTOMapper studentUserPrivateLessonDTOMapper,
                                  TutorUserPrivateLessonDTOMapper tutorUserPrivateLessonDTOMapper) {
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

    public PrivateLesson mapToDomain(PrivateLessonCreateDTO privateLessonCreateDTO) {
        return PrivateLesson.builder()
                .withTutor(tutorUserPrivateLessonDTOMapper.mapToDomain(privateLessonCreateDTO.tutor()))
                .withStudent(null)
                .withConnectionCode(null)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(privateLessonCreateDTO.isAvailableOffline())
                .withStartTime(privateLessonCreateDTO.startTime())
                .withEndTime(privateLessonCreateDTO.endTime())
                .build();
    }
}
