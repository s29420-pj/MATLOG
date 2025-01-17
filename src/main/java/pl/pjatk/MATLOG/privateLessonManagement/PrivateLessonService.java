package pl.pjatk.MATLOG.privateLessonManagement;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.PrivateLesson;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.enums.PrivateLessonStatus;
import pl.pjatk.MATLOG.domain.exceptions.lessonExceptions.PrivateLessonInvalidTimeException;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonCreateDTO;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonDTO;
import pl.pjatk.MATLOG.privateLessonManagement.dto.PrivateLessonDTOMapper;
import pl.pjatk.MATLOG.privateLessonManagement.persistance.PrivateLessonDAO;
import pl.pjatk.MATLOG.privateLessonManagement.persistance.PrivateLessonDAOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.StudentUserService;
import pl.pjatk.MATLOG.userManagement.tutorUser.TutorUserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        save(domainLesson);
    }

    public void bookPrivateLesson(String id, String studentId) {
        PrivateLesson privateLesson = getDomainPrivateLessonById(id);

        privateLesson.assignStudent(getStudentUserById(studentId));
        privateLesson.changeStatus(PrivateLessonStatus.BOOKED);

        privateLessonRepository.save(privateLessonDAOMapper.mapToDAO(privateLesson));
    }

    public void paidPrivateLesson(String id) {
        PrivateLesson privateLesson = getDomainPrivateLessonById(id);

        privateLesson.changeConnectionCode(generateConnectionCode());
        privateLesson.changeStatus(PrivateLessonStatus.PAID);

        privateLessonRepository.save(privateLessonDAOMapper.mapToDAO(privateLesson));
    }

    public void cancelPrivateLesson(String id) {
        PrivateLesson privateLesson = getDomainPrivateLessonById(id);

        privateLesson.unassignStudent();
        privateLesson.changeConnectionCode(null);
        privateLesson.changeStatus(PrivateLessonStatus.AVAILABLE);

        save(privateLesson);
    }

    public void deletePrivateLesson(String id) {
        privateLessonRepository.deleteById(id);
    }

    public List<PrivateLessonDTO> getAllPrivateLessons() {
        return privateLessonRepository.findAll().stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllAvailablePrivateLessons() {
        return privateLessonRepository.findAll().stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.AVAILABLE))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllBookedPrivateLessons() {
        return privateLessonRepository.findAll().stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.BOOKED))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllPaidPrivateLessons() {
        return privateLessonRepository.findAll().stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.PAID))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    // Tutor Private Lesson methods

    public List<PrivateLessonDTO> getAllPrivateLessonsByTutorId(String id) {
        return getDomainPrivateLessonsByTutorId(id).stream()
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllAvailablePrivateLessonsByTutorId(String id) {
        return getDomainPrivateLessonsByTutorId(id).stream()
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.AVAILABLE))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllBookedPrivateLessonsByTutorId(String id) {
        return getDomainPrivateLessonsByTutorId(id).stream()
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.BOOKED))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllPaidPrivateLessonsByTutorId(String id) {
        return getDomainPrivateLessonsByTutorId(id).stream()
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.PAID))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    // Student Private Lesson methods

    public List<PrivateLessonDTO> getAllPrivateLessonsByStudentId(String id) {
        return privateLessonRepository.findAllByStudent_Id(id).stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllBookedPrivateLessonsByStudentId(String id) {
        return privateLessonRepository.findAllByStudent_Id(id).stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.BOOKED))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    public List<PrivateLessonDTO> getAllPaidPrivateLessonsByStudentId(String id) {
        return privateLessonRepository.findAllByStudent_Id(id).stream()
                .map(privateLessonDAOMapper::mapToDomain)
                .filter(lesson -> lesson.getStatus().equals(PrivateLessonStatus.PAID))
                .map(privateLessonDTOMapper::mapToDTO)
                .toList();
    }

    // TODO: generate connection code with Google API or another
    private String generateConnectionCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
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

    private PrivateLesson getDomainPrivateLessonById(String id) {
        Optional<PrivateLessonDAO> byId = privateLessonRepository.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException("Private lesson not found");

        return privateLessonDAOMapper.mapToDomain(byId.get());
    }

    private StudentUser getStudentUserById(String id) {
        return studentUserService.getStudentUserById(id);
    }

    public void save(PrivateLesson privateLesson) {
        privateLessonRepository.save(privateLessonDAOMapper.mapToDAO(privateLesson));
    }
}
