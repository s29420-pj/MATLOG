package pl.pjatk.MATLOG.UserManagement.user;


import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.TutorUserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.TutorUserDAOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.UserDAOMapper;

@Component
public class TutorUserMapperFactory implements UserMapperFactory {

    private final TutorUserDTOMapper dtoMapper;
    private final TutorUserDAOMapper daoMapper;

    public TutorUserMapperFactory(TutorUserDTOMapper dtoMapper,
                                  TutorUserDAOMapper daoMapper) {
        this.dtoMapper = dtoMapper;
        this.daoMapper = daoMapper;
    }

    @Override
    public UserDAOMapper getUserDAOMapper() {
        return daoMapper;
    }

    @Override
    public UserDTOMapper getUserDTOMapper() {
        return dtoMapper;
    }
}
