package pl.pjatk.MATLOG.userManagement.tutorUser.mapper;

import pl.pjatk.MATLOG.configuration.annotations.Mapper;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.PrivateLessonTutorUserDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserRepository;

import java.util.Optional;

@Mapper
public class TutorUserPrivateLessonDTOMapper {

    private final TutorUserRepository tutorUserRepository;
    private final TutorUserDAOMapper tutorUserDAOMapper;

    public TutorUserPrivateLessonDTOMapper(TutorUserRepository tutorUserRepository,
                                           TutorUserDAOMapper tutorUserDAOMapper) {
        this.tutorUserRepository = tutorUserRepository;
        this.tutorUserDAOMapper = tutorUserDAOMapper;
    }

    public PrivateLessonTutorUserDTO mapToDTO(TutorUser tutorUser) {
        return new PrivateLessonTutorUserDTO(tutorUser.getId());
    }

    public TutorUser mapToDomain(PrivateLessonTutorUserDTO tutorUser) {
        Optional<TutorUserDAO> tutorDAO = tutorUserRepository.findById(tutorUser.id());
        if (tutorDAO.isEmpty()) throw new UserNotFoundException();
        return tutorUserDAOMapper.mapToDomain(tutorDAO.get());
    }
}
