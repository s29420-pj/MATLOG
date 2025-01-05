package pl.pjatk.MATLOG.PrivateLessonManagment.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.PrivateLesson.AvailablePrivateLesson;
import pl.pjatk.MATLOG.PrivateLessonManagment.persistance.AvailablePrivateLessonDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.PrivateLessonTutorUserDTO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserDAOMapper;

@Component
@RequiredArgsConstructor
public class AvailablePrivateLessonDAOMapper {

    private final TutorUserDAOMapper tutorUserDAOMapper;

    public AvailablePrivateLessonDAO mapToDAO(AvailablePrivateLesson availablePrivateLesson) {
        return new AvailablePrivateLessonDAO(
                availablePrivateLesson.getId(),
                tutorUserDAOMapper.createUserDAO(availablePrivateLesson.getTutor()),
                availablePrivateLesson.isAvailableOffline(),
                availablePrivateLesson.getStartTime(),
                availablePrivateLesson.getEndTime(),
                availablePrivateLesson.getPrice());
    }

    public AvailablePrivateLesson mapToDomain(AvailablePrivateLessonDAO availablePrivateLessonDAO) {
        return AvailablePrivateLesson.builder()
                .withId(availablePrivateLessonDAO.id())
                .withTutor(tutorUserDAOMapper.createUser(availablePrivateLessonDAO.tutor()))
                .withIsAvailableOffline(availablePrivateLessonDAO.isAvailableOffline())
                .withStartTime(availablePrivateLessonDAO.startTime())
                .withEndTime(availablePrivateLessonDAO.endTime())
                .withPrice(availablePrivateLessonDAO.price())
                .build();
    }
}
