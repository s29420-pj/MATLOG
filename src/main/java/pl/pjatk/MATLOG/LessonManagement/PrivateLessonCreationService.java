package pl.pjatk.MATLOG.LessonManagement;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.UserManagement.TutorUserRepository;
import pl.pjatk.MATLOG.domain.Lesson;
import pl.pjatk.MATLOG.domain.PrivateLesson;

@Service
public class PrivateLessonCreationService {

    private TutorUserRepository tutorUserRepository;

    public PrivateLessonCreationService(TutorUserRepository tutorUserRepository) {
        this.tutorUserRepository = tutorUserRepository;
    }
    
}
