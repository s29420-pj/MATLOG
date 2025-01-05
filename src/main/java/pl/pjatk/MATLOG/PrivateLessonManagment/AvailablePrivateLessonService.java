package pl.pjatk.MATLOG.PrivateLessonManagment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.PrivateLesson.AvailablePrivateLesson;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.AvailablePrivateLessonDTO;
import pl.pjatk.MATLOG.PrivateLessonManagment.mappers.AvailablePrivateLessonDAOMapper;
import pl.pjatk.MATLOG.PrivateLessonManagment.mappers.AvailablePrivateLessonDTOMapper;
import pl.pjatk.MATLOG.PrivateLessonManagment.persistance.AvailablePrivateLessonDAO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.TutorUserService;

@Service
@RequiredArgsConstructor
public class AvailablePrivateLessonService {

    private final AvailablePrivateLessonRepository availablePrivateLessonRepository;
    private final AvailablePrivateLessonDTOMapper availablePrivateLessonDTOMapper;
    private final AvailablePrivateLessonDAOMapper availablePrivateLessonDAOMapper;
    private final TutorUserService tutorUserService;

    public void createAvailablePrivateLesson(AvailablePrivateLessonDTO availablePrivateLessonDTO) {

        AvailablePrivateLesson lesson = availablePrivateLessonDTOMapper.mapToDomain(
                availablePrivateLessonDTO,
                tutorUserService.findUserByEmailAddress(availablePrivateLessonDTO.tutor().email())
        );

        availablePrivateLessonRepository.save(availablePrivateLessonDAOMapper.mapToDAO(lesson));
    }

}