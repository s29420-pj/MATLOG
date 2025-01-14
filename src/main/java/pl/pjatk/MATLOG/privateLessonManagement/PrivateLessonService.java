package pl.pjatk.MATLOG.privateLessonManagement;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.PrivateLessonInvalidTimeException;
import pl.pjatk.MATLOG.domain.PrivateLesson;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonCreateDTO;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonDTO;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonDTOMapper;
import pl.pjatk.MATLOG.privateLessonManagement.persistance.PrivateLessonDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.StudentUserService;
import pl.pjatk.MATLOG.userManagement.tutorUser.TutorUserService;

import java.util.List;

@Service
@Transactional
public class PrivateLessonService {

    private final PrivateLessonRepository privateLessonRepository;
    private final PrivateLessonDAOMapper privateLessonDAOMapper;
    private final PrivateLessonDTOMapper privateLessonDTOMapper;
    private final StudentUserService studentUserService;
    private final TutorUserService tutorUserService;

    public PrivateLessonService(PrivateLessonRepository privateLessonRepository,
                                PrivateLessonDAOMapper privateLessonDAOMapper,
                                PrivateLessonDTOMapper privateLessonDTOMapper,
                                StudentUserService studentUserService,
                                TutorUserService tutorUserService) {
        this.privateLessonRepository = privateLessonRepository;
        this.privateLessonDAOMapper = privateLessonDAOMapper;
        this.privateLessonDTOMapper = privateLessonDTOMapper;
        this.studentUserService = studentUserService;
        this.tutorUserService = tutorUserService;
    }

    public void createPrivateLesson(PrivateLessonCreateDTO privateLesson) {
        if (privateLesson == null) throw new IllegalArgumentException("Private lesson cannot be null");

        if (hasConflict(privateLesson)) throw new PrivateLessonInvalidTimeException();

        PrivateLesson domainLesson = privateLessonDTOMapper.mapToDomain(privateLesson,
                tutorUserService.getTutorUserById(privateLesson.tutorId()),
                null, null);

        privateLessonRepository.save(privateLessonDAOMapper.mapToDAO(domainLesson));
    }

    public List<PrivateLessonDTO> getPrivateLessonsByTutorId(String id) {
        return getDomainPrivateLessonsByTutorId(id).stream()
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    private boolean hasConflict(PrivateLessonCreateDTO privateLessonCreateDTO) {
        return privateLessonRepository.findAllByTutor_Id(privateLessonCreateDTO.tutorId())
                .stream()
                .anyMatch(lesson ->
                        lesson.getStartTime().isBefore(privateLessonCreateDTO.endTime())
                        && lesson.getEndTime().isAfter(privateLessonCreateDTO.startTime()));
    }

    private List<PrivateLesson> getDomainPrivateLessonsByTutorId(String id) {
        return privateLessonRepository.findAllByTutor_Id(id).stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .toList();
    }
}
