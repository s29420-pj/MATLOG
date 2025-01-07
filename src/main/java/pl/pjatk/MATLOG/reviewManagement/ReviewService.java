package pl.pjatk.MATLOG.reviewManagement;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.UserManagement.studentUser.StudentUserService;
import pl.pjatk.MATLOG.UserManagement.tutorUser.TutorUserService;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewCreationDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewLookUpDTOMapper;

import java.util.List;

/**
 * Service layer class for Review. It uses repository layer to get data from database, </br>
 * map it to dto and give that data to Controller which is related to Review operations.
 */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDAOMapper reviewDAOMapper;
    private final ReviewLookUpDTOMapper reviewLookUpDTOMapper;
    private final ReviewCreationDTOMapper reviewCreationDTOMapper;
    private final StudentUserService studentUserService;
    private final TutorUserService tutorUserService;


    public ReviewService(ReviewRepository reviewRepository, ReviewDAOMapper reviewDAOMapper,
                         ReviewLookUpDTOMapper reviewLookUpDTOMapper,
                         ReviewCreationDTOMapper reviewCreationDTOMapper, StudentUserService studentUserService, TutorUserService tutorUserService) {
        this.reviewRepository = reviewRepository;
        this.reviewDAOMapper = reviewDAOMapper;
        this.reviewLookUpDTOMapper = reviewLookUpDTOMapper;
        this.reviewCreationDTOMapper = reviewCreationDTOMapper;
        this.studentUserService = studentUserService;
        this.tutorUserService = tutorUserService;
    }

    /**
     * Method which is used to obtain all of Tutor's Reviews.
     * @param emailAddress Email address of the Tutor.
     * @return List of ReviewLookUpDTO.
     */
    public List<ReviewLookUpDTO> getTutorReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByTutor_EmailAddress(emailAddress).stream()
                .map(reviewDAOMapper::create)
                .map(reviewLookUpDTOMapper::create)
                .toList();
    }

    /**
     * Method which is used to obtain all of Student's Reviews.
     * @param emailAddress Email address of the Student.
     * @return List of ReviewLookUpDTO.
     */
    public List<ReviewLookUpDTO> getStudentReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByStudent_EmailAddress(emailAddress).stream()
                .map(reviewDAOMapper::create)
                .map(reviewLookUpDTOMapper::create)
                .toList();
    }

    /**
     * Method which is used to save Review to the database.
     * @param creationDTO DTO representation of the Review
     * @throws AuthenticationException When user with provided email address does not exist.
     */
    public void saveReview(ReviewCreationDTO creationDTO) throws AuthenticationException {
        Review review = reviewCreationDTOMapper.create(creationDTO,
                studentUserService.findUserByEmailAddress(creationDTO.studentUserEmailAddress()),
                tutorUserService.findUserByEmailAddress(creationDTO.tutorUserEmailAddress()));
        reviewRepository.save(reviewDAOMapper.create(review));
    }
}
