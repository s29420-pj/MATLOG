package pl.pjatk.MATLOG.PrivateLessonManagment.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.PrivateLesson.AvailablePrivateLesson;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.AvailablePrivateLessonDTO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserDTOMapper;

@Component
@RequiredArgsConstructor
public class AvailablePrivateLessonDTOMapper {

    private final TutorUserDTOMapper tutorUserDTOMapper;

    public AvailablePrivateLessonDTO mapToDTO(AvailablePrivateLesson availablePrivateLesson) {
        return new AvailablePrivateLessonDTO(
                availablePrivateLesson.getId(),
                tutorUserDTOMapper.mapToDTO(availablePrivateLesson.getTutor()),
                availablePrivateLesson.isAvailableOffline(),
                availablePrivateLesson.getStartTime(),
                availablePrivateLesson.getEndTime(),
                availablePrivateLesson.getPrice());
    }

    public AvailablePrivateLesson mapToDomain(AvailablePrivateLessonDTO availablePrivateLessonDTO, TutorUser tutor) {
        return AvailablePrivateLesson.builder()
                .withTutor(tutor)
                .withIsAvailableOffline(availablePrivateLessonDTO.isAvailableOffline())
                .withStartTime(availablePrivateLessonDTO.startTime())
                .withEndTime(availablePrivateLessonDTO.endTime())
                .withPrice(availablePrivateLessonDTO.price())
                .build();
    }
}
