package pl.pjatk.MATLOG.UserManagement.user.tutor.mapper;

import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.UserManagement.user.tutor.dto.TutorUserReviewCreationDTO;

@Component
public class TutorUserReviewDTOMapper {

    public TutorUserReviewCreationDTO createTutorReviewCreationDTO(TutorUser tutorUser) {
        return new TutorUserReviewCreationDTO(tutorUser.getFirstName(),
                tutorUser.getLastName(),
                tutorUser.getEmailAddress());
    }
}
