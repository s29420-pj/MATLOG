package pl.pjatk.MATLOG.privateLessonManagement.dto;

import pl.pjatk.MATLOG.domain.PrivateLesson;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserPrivateLessonDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserPrivateLessonDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;

@Mapper
public class PrivateLessonDTOMapper {

    public PrivateLessonDTO mapToDTO(PrivateLesson privateLesson) {
        return new PrivateLessonDTO(
            privateLesson.getId(),
            privateLesson.getTutor().getId(),
            privateLesson.getStudent().getId(),
            privateLesson.isAvailableOffline(),
            privateLesson.getStartTime(),
            privateLesson.getEndTime(),
            privateLesson.getPrice()
        );
    }

    public PrivateLesson mapToDomain(PrivateLessonCreateDTO privateLessonCreateDTO,
                                     TutorUser tutorUser,
                                     StudentUser studentUser,
                                     String connectionCode) {
        return PrivateLesson.builder()
                .withTutor(tutorUser)
                .withStudent(studentUser)
                .withConnectionCode(connectionCode)
                .withStatus(PrivateLessonStatus.AVAILABLE)
                .withIsAvailableOffline(privateLessonCreateDTO.isAvailableOffline())
                .withStartTime(privateLessonCreateDTO.startTime())
                .withEndTime(privateLessonCreateDTO.endTime())
                .build();
    }
}
