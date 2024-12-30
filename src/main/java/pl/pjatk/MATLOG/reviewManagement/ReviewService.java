package pl.pjatk.MATLOG.reviewManagement;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.StudentUser;
import pl.pjatk.MATLOG.Domain.TutorUser;
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

    public ReviewService(ReviewRepository reviewRepository, ReviewDAOMapper reviewDAOMapper, ReviewLookUpDTOMapper reviewLookUpDTOMapper, ReviewCreationDTOMapper reviewCreationDTOMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewDAOMapper = reviewDAOMapper;
        this.reviewLookUpDTOMapper = reviewLookUpDTOMapper;
        this.reviewCreationDTOMapper = reviewCreationDTOMapper;
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

    public void saveReview(ReviewCreationDTO creationDTO, StudentUser studentUser, TutorUser tutorUser) {
        ReviewDAO reviewDAO = reviewDAOMapper.create(reviewCreationDTOMapper.create(creationDTO, studentUser, tutorUser));
        reviewRepository.save(reviewDAO);
    }
}
