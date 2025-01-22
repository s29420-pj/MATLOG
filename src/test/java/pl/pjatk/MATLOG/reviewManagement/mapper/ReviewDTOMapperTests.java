package pl.pjatk.MATLOG.reviewManagement.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.MATLOG.domain.Review;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReviewDTOMapperTests {

    private final ReviewDTOMapper mapper = new ReviewDTOMapper();

    @Test
    void shouldMapReviewToDTO() {
        Review review = Review.builder()
                .withRate(Rate.ONE)
                .withStudent(new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "testName"))
                .withComment("testComment")
                .build();

        ReviewDTO reviewDTO = mapper.mapToDTO(review);

        assertThat(reviewDTO.id()).isEqualTo(review.getId());
        assertThat(reviewDTO.studentUserReviewLookUpDTO()).isEqualTo(review.getStudentUser());
        assertThat(reviewDTO.rate()).isEqualTo(review.getRate());
        assertThat(reviewDTO.comment()).isEqualTo(review.getComment());
        assertThat(reviewDTO.dateAndTimeOfReview()).isEqualTo(review.getDateAndTimeOfComment());
    }

    @Test
    void shouldMapDTOToReview() {
        ReviewDTO reviewDTO = new ReviewDTO(
                "1",
                new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "student123"),
                Rate.FIVE,
                "Great job!",
                LocalDateTime.now()
        );

        Review review = mapper.mapToDomain(reviewDTO);

        assertThat(review.getId()).isEqualTo(reviewDTO.id());
        assertThat(review.getStudentUser()).isEqualTo(reviewDTO.studentUserReviewLookUpDTO());
        assertThat(review.getRate()).isEqualTo(reviewDTO.rate());
        assertThat(review.getComment()).isEqualTo(reviewDTO.comment());
        assertThat(review.getDateAndTimeOfComment()).isEqualTo(reviewDTO.dateAndTimeOfReview());
    }

    @Test
    void shouldMapCreationDTOToReview() {
        ReviewCreationDTO creationDTO = new ReviewCreationDTO(
                Rate.FOUR,
                UUID.randomUUID().toString(),
                "testComment",
                LocalDateTime.now(),
                new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "testName")
        );

        Review review = mapper.mapToDomain(creationDTO);

        assertThat(review.getStudentUser()).isEqualTo(creationDTO.studentUser());
        assertThat(review.getRate()).isEqualTo(creationDTO.rate());
        assertThat(review.getComment()).isEqualTo(creationDTO.comment());
        assertThat(review.getDateAndTimeOfComment()).isEqualTo(creationDTO.dateAndTimeOfReview());
    }
}
