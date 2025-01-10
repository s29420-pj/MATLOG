package pl.pjatk.MATLOG.PrivateLessonManagment;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions.PrivateLessonInvalidTimeException;
import pl.pjatk.MATLOG.Domain.PrivateLesson;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.PrivateLessonCreateDTO;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.PrivateLessonDTO;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.PrivateLessonDTOMapper;
import pl.pjatk.MATLOG.PrivateLessonManagment.persistance.PrivateLessonDAOMapper;

import java.util.List;

@Service
@Transactional
public class PrivateLessonService {

    private final PrivateLessonRepository privateLessonRepository;
    private final PrivateLessonDAOMapper privateLessonDAOMapper;
    private final PrivateLessonDTOMapper privateLessonDTOMapper;

    public PrivateLessonService(PrivateLessonRepository privateLessonRepository, PrivateLessonDAOMapper privateLessonDAOMapper, PrivateLessonDTOMapper privateLessonDTOMapper) {
        this.privateLessonRepository = privateLessonRepository;
        this.privateLessonDAOMapper = privateLessonDAOMapper;
        this.privateLessonDTOMapper = privateLessonDTOMapper;
    }

    public void createPrivateLesson(PrivateLessonCreateDTO privateLesson) {
        if (privateLesson == null) throw new IllegalArgumentException("Private lesson cannot be null");

        List<PrivateLesson> existingLesson = privateLessonRepository.findAllByTutor_Id(privateLesson.tutor().id())
                .stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .toList();

        boolean hasConflict = existingLesson.stream()
                .anyMatch(lesson -> lesson.getStartTime().isBefore(privateLesson.endTime())
                        && lesson.getEndTime().isAfter(privateLesson.startTime()));

        if (hasConflict) throw new PrivateLessonInvalidTimeException();
    }

    public List<PrivateLessonDTO> getPrivateLessonsByTutorId(String id) {
        return getDomainPrivateLessonsByTutorId(id).stream()
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    private List<PrivateLesson> getDomainPrivateLessonsByTutorId(String id) {
        return privateLessonRepository.findAllByTutor_Id(id).stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .toList();
    }
}
