package pl.pjatk.MATLOG.PrivateLessonManagment;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.PrivateLesson;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;

import java.util.List;
import java.util.Set;

@Service
public class PrivateLessonService {

    private final PrivateLessonRepository privateLessonRepository;

    public PrivateLessonService(PrivateLessonRepository privateLessonRepository) {
        this.privateLessonRepository = privateLessonRepository;
    }

    public void createPrivateLesson(PrivateLesson privateLesson) {
        if (privateLesson == null) throw new IllegalArgumentException("Private lesson cannot be null");

        findByTutorId(privateLesson.getTutorId()).forEach(lesson -> {
            if (privateLesson.getStartTime().isBefore(lesson.getEndTime()) && privateLesson.getEndTime().isAfter(lesson.getStartTime())) {
                throw new IllegalArgumentException("Tutor already has a lesson at this time");
            }
        });

        privateLessonRepository.save(privateLesson);
    }

    public void updatePrivateLesson(PrivateLesson privateLesson) {
        if (privateLesson == null) throw new IllegalArgumentException("Private lesson cannot be null");
        privateLessonRepository.save(privateLesson);
    }

    public void cancelPrivateLesson(String id) {
        // add logic that sends email to tutor and student if lesson is cancelled
        // add logic to refund student if lesson was paid and lesson is cancelled by tutor
        // add logic to not refund student if lesson was paid and lesson cancelled by student less than time in refund policy
        // add logic to refund student if lesson was paid and lesson cancelled by student more than time in refund policy
    }

    public void bookPrivateLesson(String id, String studentId) {
        PrivateLesson privateLesson = privateLessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Private lesson not found"));

        if (privateLesson.getStatus() != PrivateLessonStatus.AVAILABLE) throw new IllegalArgumentException("Private lesson is not available");

        findByStudentId(privateLesson.getStudentId()).forEach(lesson -> {
            if (privateLesson.getStartTime().isBefore(lesson.getEndTime()) && privateLesson.getEndTime().isAfter(lesson.getStartTime())) {
                throw new IllegalArgumentException("Student already has a lesson at this time");
            }
        });

        privateLesson.setStatus(PrivateLessonStatus.BOOKED);

        // paymeny logic, payment status

        privateLesson.setStudentId(studentId);
        // send email to student with Payment link
        // check if student paid
        // if paid, change status to PAID
        // if not paid, change status to AVAILABLE and send email to student with information about payment
        // send email to tutor
        // send email to student with connection code
        privateLessonRepository.save(privateLesson);
    }

    // Additional repository methods

    public Set<PrivateLesson> findByTutorId(String tutorId) {
        return privateLessonRepository.findByTutorId(tutorId);
    }

    public Set<PrivateLesson> findByStudentId(String studentId) {
        return privateLessonRepository.findByStudentId(studentId);
    }
}
