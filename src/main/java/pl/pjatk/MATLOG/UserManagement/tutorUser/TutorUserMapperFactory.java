package pl.pjatk.MATLOG.UserManagement.tutorUser;


import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.tutorUser.mapper.TutorUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.tutorUser.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.tutorUser.mapper.TutorUserReviewDTOMapper;

@Component
public class TutorUserMapperFactory {

    private final TutorUserDTOMapper dtoMapper;
    private final TutorUserDAOMapper daoMapper;
    private final TutorUserReviewDTOMapper tutorUserReviewDTOMapper;

    public TutorUserMapperFactory(TutorUserDTOMapper dtoMapper,
                                  TutorUserDAOMapper daoMapper, TutorUserReviewDTOMapper tutorUserReviewDTOMapper) {
        this.dtoMapper = dtoMapper;
        this.daoMapper = daoMapper;
        this.tutorUserReviewDTOMapper = tutorUserReviewDTOMapper;
    }

    public TutorUserDAOMapper getUserDAOMapper() {
        return daoMapper;
    }

    public TutorUserDTOMapper getUserDTOMapper() {
        return dtoMapper;
    }

    public TutorUserReviewDTOMapper getTutorUserReviewDTOMapper() {return  tutorUserReviewDTOMapper;}
}
