package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions.PrivateLessonInvalidTimeException;
import pl.pjatk.MATLOG.PrivateLessonManagment.dto.PrivateLessonCreateDTO;

import java.util.List;

@Service
public class PrivateLessonService {

    private final PrivateLessonRepository privateLessonRepository;

    public PrivateLessonService(PrivateLessonRepository privateLessonRepository) {
        this.privateLessonRepository = privateLessonRepository;
    }

    public void createPrivateLesson(PrivateLessonCreateDTO privateLesson) {
        if (privateLesson == null) throw new IllegalArgumentException("Private lesson cannot be null");

        List<PrivateLesson> existingLesson = privateLessonRepository.findAllById(privateLesson.tutorId());

        boolean hasConflict = existingLesson.stream()
                .anyMatch(lesson -> lesson.getStartTime().isBefore(privateLesson.endTime())
                        && lesson.getEndTime().isAfter(privateLesson.startTime()));

        if (hasConflict) throw new PrivateLessonInvalidTimeException();


}
}
