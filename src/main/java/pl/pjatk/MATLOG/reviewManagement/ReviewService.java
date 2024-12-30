package pl.pjatk.MATLOG.reviewManagement;

import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDTOMapper;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDAOMapper daoMapper;
    private final ReviewDTOMapper dtoMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewDAOMapper daoMapper, ReviewDTOMapper dtoMapper) {
        this.reviewRepository = reviewRepository;
        this.daoMapper = daoMapper;
        this.dtoMapper = dtoMapper;
    }

    public List<ReviewDTO> getTutorReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByTutor_EmailAddress(emailAddress).stream()
                .map(daoMapper::create)
                .map(dtoMapper::create)
                .toList();
    }

    public List<ReviewDTO> getStudentReviewsDTOByEmailAddress(String emailAddress) {
        return reviewRepository.findAllByStudent_EmailAddress(emailAddress).stream()
                .map(daoMapper::create)
                .map(dtoMapper::create)
                .toList();
    }
}
