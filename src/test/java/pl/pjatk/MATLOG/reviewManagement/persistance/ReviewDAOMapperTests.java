package pl.pjatk.MATLOG.reviewManagement.persistance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.MATLOG.domain.Review;
import pl.pjatk.MATLOG.domain.StudentUser;
import pl.pjatk.MATLOG.domain.enums.Rate;
import pl.pjatk.MATLOG.userManagement.studentUser.dto.StudentUserReviewLookUpDTO;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewDAOMapperTests {
    private final StudentUserDAOMapper studentUserDAOMapper = mock(StudentUserDAOMapper.class);
    private final StudentUserReviewDTOMapper studentUserReviewDTOMapper = mock(StudentUserReviewDTOMapper.class);
    private final ReviewDAOMapper reviewDAOMapper = new ReviewDAOMapper(studentUserDAOMapper, studentUserReviewDTOMapper);

    @Test
    void shouldMapReviewToDAO() {
        StudentUserReviewLookUpDTO studentDTO = new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "student123");
        StudentUserDAO studentDAO = mock(StudentUserDAO.class);
        LocalDateTime now = LocalDateTime.now();
        Review review = Review.builder()
                .withId("1")
                .withStudent(studentDTO)
                .withRate(Rate.FIVE)
                .withComment("Excellent")
                .withDateAndTimeOfReview(now)
                .build();

        when(studentUserDAOMapper.mapToDAO(studentDTO)).thenReturn(studentDAO);

        ReviewDAO reviewDAO = reviewDAOMapper.mapToDAO(review);

        assertThat(reviewDAO.id).isEqualTo(review.getId());
        assertThat(reviewDAO.comment).isEqualTo(review.getComment());
        assertThat(reviewDAO.rate).isEqualTo(review.getRate());
        assertThat(reviewDAO.dateAndTimeOfReview).isEqualTo(review.getDateAndTimeOfComment());
        assertThat(reviewDAO.student).isEqualTo(studentDAO);
    }

    @Test
    void shouldMapDAOToReview() {
        StudentUserDAO studentDAO = mock(StudentUserDAO.class);
        StudentUser studentUser = mock(StudentUser.class);
        StudentUserReviewLookUpDTO studentDTO = new StudentUserReviewLookUpDTO(UUID.randomUUID().toString(), "student123");
        LocalDateTime now = LocalDateTime.now();
        ReviewDAO reviewDAO = new ReviewDAO("1", "Excellent", Rate.FIVE, now, studentDAO);

        when(studentUserDAOMapper.mapToDomain(studentDAO)).thenReturn(studentUser);
        when(studentUserReviewDTOMapper.mapToStudentReviewLookUpDTO(studentUser)).thenReturn(studentDTO);

        Review review = reviewDAOMapper.mapToDomain(reviewDAO);

        assertThat(review.getId()).isEqualTo(reviewDAO.id);
        assertThat(review.getComment()).isEqualTo(reviewDAO.comment);
        assertThat(review.getRate()).isEqualTo(reviewDAO.rate);
        assertThat(review.getDateAndTimeOfComment()).isEqualTo(reviewDAO.dateAndTimeOfReview);
        assertThat(review.getStudentUser()).isEqualTo(studentDTO);
    }
}
