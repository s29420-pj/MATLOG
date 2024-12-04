package pl.pjatk.MATLOG.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Class representing Private Lesson in application.
 * Private Lesson is a lesson that is taught by Tutor to Student.
 * It can be created by Tutor to create slot in the calendar that can be booked by Student.
 */
@Getter
@Document("private_lesson")
public final class PrivateLesson {

    @MongoId
    private final String id;
    private final List<SchoolSubject> schoolSubjects;
    private final String tutorId;
    private final String studentId;
    private final String connectionCode;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Double price;

    /**
     * Private Constructor of the PrivateLesson. This constructor is used by the Builder.
     *
     * @param schoolSubjects List of SchoolSubjects that will be taught during the lesson.
     * @param tutorId Identification of Tutor that will lead the lesson
     * @param startTime Start time of the lesson
     * @param endTime End time of the lesson
     * @param price Price of the lesson
     *
     * @throws PrivateLessonInvalidSchoolSubjectException When schoolSubjects are empty
     * @throws PrivateLessonInvalidIdException When tutorId is empty
     * @throws IllegalStateException When start time is empty
     * @throws IllegalStateException When end time is empty
     * @throws PrivateLessonInvalidPriceException When price is null or negative
     * @throws PrivateLessonInvalidStartTimeException When start time is before current time
     * @throws PrivateLessonInvalidEndTimeException When end time is before current time, before start time or equals start time
     */
    @Builder(setterPrefix = "with")
    private PrivateLesson(List<SchoolSubject> schoolSubjects, String tutorId, LocalDateTime startTime, LocalDateTime endTime, Double price) {
        this.id = UUID.randomUUID().toString();
        this.schoolSubjects = schoolSubjects;
        this.tutorId = tutorId;
        this.studentId = "not assigned yet";
        this.connectionCode = "not assigned yet";
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        validateFields();
    }

    /**
     * Method that validates all fields of the PrivateLesson.
     */
    private void validateFields() {
        if (schoolSubjects == null) throw new PrivateLessonInvalidSchoolSubjectException();
        if (tutorId == null || tutorId.isEmpty()) throw new PrivateLessonInvalidIdException();
        if (startTime == null) throw new IllegalStateException("Start time of private lesson is mandatory and must be set");
        if (endTime == null) throw new IllegalStateException("End time of private lesson is mandatory and must be set");
        if (price == null) {
            throw new PrivateLessonInvalidPriceException("Price of private lesson is mandatory and must be set");
        } else if (price < 0) {
            throw new PrivateLessonInvalidPriceException();
        }

        if (startTime.isBefore(LocalDateTime.now()))
            throw new PrivateLessonInvalidStartTimeException();

        if (endTime.isBefore(LocalDateTime.now()) || endTime.isBefore(startTime) || endTime.isEqual(startTime))
            throw new PrivateLessonInvalidEndTimeException();
    }
}