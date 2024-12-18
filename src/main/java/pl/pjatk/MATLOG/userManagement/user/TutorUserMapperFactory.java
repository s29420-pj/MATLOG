package pl.pjatk.MATLOG.UserManagement.user;


import org.springframework.stereotype.Component;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.User;
import pl.pjatk.MATLOG.UserManagement.user.dto.UserDTO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.UserDAO;
import pl.pjatk.MATLOG.UserManagement.user.persistance.mapper.TutorUserDAOMapper;

@Component
public class TutorUserMapperFactory implements UserMapperFactory {

    private final TutorUserDAOMapper daoMapper;

    public TutorUserMapperFactory(TutorUserDAOMapper daoMapper) {
        this.daoMapper = daoMapper;
    }

    @Override
    public UserDTO createDTO() {
        return null;
    }

    @Override
    public UserDAO createDAO(User tutorUser) {
        return daoMapper.createTutorUserDAO(tutorUser);
    }
}
