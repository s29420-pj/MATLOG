package pl.pjatk.MATLOG.userManagement.tutorUser.persistance;

import org.springframework.web.reactive.function.client.WebClient;
import pl.pjatk.MATLOG.domain.Review;
import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.userManagement.studentUser.mapper.StudentUserReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAO;
import pl.pjatk.MATLOG.userManagement.studentUser.persistance.StudentUserDAOMapper;

import java.net.URI;
import java.util.Objects;

@Mapper
public class ReviewDAOMapper {

    private final StudentUserDAOMapper studentUserDAOMapper;
    private final StudentUserReviewDTOMapper studentUserReviewDTOMapper;
    private final WebClient webClient;


    public ReviewDAOMapper(StudentUserDAOMapper studentUserDAOMapper,
                           StudentUserReviewDTOMapper studentUserReviewDTOMapper, WebClient webClient) {
        this.studentUserDAOMapper = studentUserDAOMapper;
        this.studentUserReviewDTOMapper = studentUserReviewDTOMapper;
        this.webClient = webClient;
    }

    public ReviewDAO mapToDAO(Review review) {
        StudentUserDAO student = Objects.requireNonNull(webClient.get()
                        .uri(URI.create("/student/user/controller/get/" + review.getStudentUser().id()))
                        .retrieve()
                        .bodyToMono(StudentUserDAO.class)
                        .retry(3)
                        .block());
        return new ReviewDAO(
            review.getId(),
            review.getComment(),
            review.getRate(),
            review.getDateAndTimeOfComment(),
            student
        );
    }

    public Review mapToDomain(ReviewDAO reviewDAO) {
        return Review.builder()
                .withId(reviewDAO.id)
                .withRate(reviewDAO.rate)
                .withComment(reviewDAO.comment)
                .withDateAndTimeOfReview(reviewDAO.dateAndTimeOfReview)
                .withStudent(studentUserReviewDTOMapper.mapToStudentReviewLookUpDTO(
                        studentUserDAOMapper.mapToDomain(reviewDAO.student)
                ))
                .build();
    }
}
