package pl.pjatk.MATLOG.UserManagement.user;


import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.TutorUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.TutorUserDAOMapper;

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
