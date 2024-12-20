package pl.pjatk.MATLOG.UserManagement.user;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.UserManagement.user.dto.mapper.UserDTOMapper;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.UserDAOMapper;

@Component
public class TutorUserMapperFactory implements UserMapperFactory {

    private final UserDTOMapper dtoMapper;
    private final UserDAOMapper daoMapper;

    public TutorUserMapperFactory(@Qualifier("tutorUserDTOMapper") UserDTOMapper dtoMapper,
                                  @Qualifier("tutorUserDAOMapper") UserDAOMapper daoMapper) {
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
