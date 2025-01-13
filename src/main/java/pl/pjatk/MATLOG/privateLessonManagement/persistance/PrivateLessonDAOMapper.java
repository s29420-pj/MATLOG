package pl.pjatk.MATLOG.privateLessonManagement.persistance;

import pl.pjatk.MATLOG.domain.PrivateLesson;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonCreateDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;

@Mapper
public class PrivateLessonDAOMapper {

    private final TutorUserDAOMapper tutorUserDAOMapper;
    private final StudentUserDAOMapper studentUserDAOMapper;

    public PrivateLessonDAOMapper(TutorUserDAOMapper tutorUserDAOMapper, StudentUserDAOMapper studentUserDAOMapper) {
        this.tutorUserDAOMapper = tutorUserDAOMapper;
        this.studentUserDAOMapper = studentUserDAOMapper;
    }

    public PrivateLesson mapToDomain(PrivateLessonDAO privateLessonDAO) {
        return PrivateLesson.builder()
                .withId(privateLessonDAO.id)
                .withTutor(tutorUserDAOMapper.mapToDomain(privateLessonDAO.tutor))
                .withStudent(studentUserDAOMapper.mapToDomain(privateLessonDAO.student))
                .withStatus(privateLessonDAO.status)
                .withStartTime(privateLessonDAO.startTime)
                .withEndTime(privateLessonDAO.endTime)
                .withPrice(privateLessonDAO.price)
                .withIsAvailableOffline(privateLessonDAO.isAvailableOffline)
                .withConnectionCode(privateLessonDAO.connectionCode)
                .build();
    }

    public PrivateLessonDAO mapToDAO(PrivateLesson privateLesson) {
        return new PrivateLessonDAO(
                privateLesson.getId(),
                tutorUserDAOMapper.mapToDAO(privateLesson.getTutor()),
                studentUserDAOMapper.mapToDAO(privateLesson.getStudent()),
                privateLesson.getConnectionCode(),
                privateLesson.getStatus(),
                privateLesson.isAvailableOffline(),
                privateLesson.getStartTime(),
                privateLesson.getEndTime(),
                privateLesson.getPrice()
        );
    }
}
