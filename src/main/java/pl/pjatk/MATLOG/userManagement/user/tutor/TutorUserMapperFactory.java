package pl.pjatk.MATLOG.userManagement.user.tutor;


import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.userManagement.user.tutor.mapper.TutorUserDTOMapper;
import pl.pjatk.MATLOG.userManagement.user.tutor.mapper.TutorUserDAOMapper;

@Component
public class TutorUserMapperFactory {

    private final TutorUserDTOMapper dtoMapper;
    private final TutorUserDAOMapper daoMapper;

    public TutorUserMapperFactory(TutorUserDTOMapper dtoMapper,
                                  TutorUserDAOMapper daoMapper) {
        this.dtoMapper = dtoMapper;
        this.daoMapper = daoMapper;
    }

    public TutorUserDAOMapper getUserDAOMapper() {
        return daoMapper;
    }

    public TutorUserDTOMapper getUserDTOMapper() {
        return dtoMapper;
    }
}
