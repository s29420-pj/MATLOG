package pl.pjatk.MATLOG.reviewManagement;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewLookUpDTO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewLookUpDTOMapper;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDAOMapper daoMapper;
    private final ReviewLookUpDTOMapper dtoMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewDAOMapper daoMapper, ReviewLookUpDTOMapper dtoMapper) {
        this.reviewRepository = reviewRepository;
        this.daoMapper = daoMapper;
        this.dtoMapper = dtoMapper;
    }

    public List<ReviewLookUpDTO> getTutorReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByTutor_EmailAddress(emailAddress).stream()
                .map(daoMapper::create)
                .map(dtoMapper::create)
                .toList();
    }

    public List<ReviewLookUpDTO> getStudentReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByStudent_EmailAddress(emailAddress).stream()
                .map(daoMapper::create)
                .map(dtoMapper::create)
                .toList();
    }

    public void saveReview(ReviewLookUpDTO)
}
