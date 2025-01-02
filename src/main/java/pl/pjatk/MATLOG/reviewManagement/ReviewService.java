package pl.pjatk.MATLOG.reviewManagement;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.user.student.StudentUserService;
import pl.pjatk.MATLOG.UserManagement.user.student.dto.StudentUserReviewCreationDTO;
import pl.pjatk.MATLOG.UserManagement.user.student.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.tutor.TutorUserService;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.TutorUserReviewCreationDTO;
import pl.pjatk.MATLOG.UserManagement.user.tutor.mapper.TutorUserReviewDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewCreationDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewLookUpDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;

import java.util.List;

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

    public List<ReviewLookUpDTO> getTutorReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByTutor_EmailAddress(emailAddress).stream()
                .map(reviewDAOMapper::create)
                .map(reviewLookUpDTOMapper::create)
                .toList();
    }

    public List<ReviewLookUpDTO> getStudentReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByStudent_EmailAddress(emailAddress).stream()
                .map(reviewDAOMapper::create)
                .map(reviewLookUpDTOMapper::create)
                .toList();
    }

    public void saveReview(ReviewCreationDTO creationDTO, StudentUserReviewCreationDTO studentUser, TutorUserReviewCreationDTO tutorUser) throws AuthenticationException {
        Review review = reviewCreationDTOMapper.create(creationDTO,
                studentUserService.findUserByEmailAddress(studentUser.emailAddress()),
                tutorUserService.findUserByEmailAddress(tutorUser.emailAddress()));
        reviewRepository.save(reviewDAOMapper.create(review));
    }
}
