package pl.pjatk.MATLOG.reviewManagement;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.Review;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;
import pl.pjatk.MATLOG.reviewManagement.exceptions.ReviewNotFoundException;
import pl.pjatk.MATLOG.reviewManagement.mapper.ReviewDTOMapper;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAO;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewDAOMapper;
import pl.pjatk.MATLOG.reviewManagement.persistance.ReviewRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDAOMapper reviewDAOMapper;
    private final ReviewDTOMapper reviewDTOMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewDAOMapper reviewDAOMapper, ReviewDTOMapper reviewDTOMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewDAOMapper = reviewDAOMapper;
        this.reviewDTOMapper = reviewDTOMapper;
    }

    public ReviewDTO findReviewDTOById(String id) {
        return reviewDTOMapper.mapToDTO(findReviewById(id));
    }

    public Review mapToDomain(ReviewCreationDTO reviewCreationDTO) {
        return reviewDTOMapper.mapToDomain(reviewCreationDTO);
    }

    public Review mapToDomain(ReviewDTO reviewDTO) {
        return reviewDTOMapper.mapToDomain(reviewDTO);
    }

    public ReviewDAO save(ReviewCreationDTO reviewCreationDTO) {
        Review review = reviewDTOMapper.mapToDomain(reviewCreationDTO);
        return reviewRepository.save(reviewDAOMapper.mapToDAO(review));
    }

    public void remove(String id) {
        reviewRepository.removeById(id);
    }


    public Review findReviewById(String id) {
        Optional<ReviewDAO> reviewDAO = reviewRepository.findById(id);
        if (reviewDAO.isEmpty()) throw new ReviewNotFoundException();
        return reviewDAOMapper.mapToDomain(reviewDAO.get());
    }
}
