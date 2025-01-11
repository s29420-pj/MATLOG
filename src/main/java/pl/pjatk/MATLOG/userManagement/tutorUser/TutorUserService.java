package pl.pjatk.MATLOG.userManagement.tutorUser;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.domain.Review;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.ReviewDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.ReviewDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.ReviewDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserRepository;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.Collection;
import java.util.Optional;

/**
 * TutorUserService class which talks to repository layer to get data from database.
 * It's used by Controller related to tutor operations.
 */
@Service
@Transactional
public class TutorUserService {

    private final TutorUserRepository tutorUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TutorUserDAOMapper tutorUserDAOMapper;
    private final TutorUserDTOMapper tutorUserDTOMapper;
    private final UserPasswordValidator passwordValidator;
    private final ReviewDTOMapper reviewDTOMapper;
    private final ReviewDAOMapper reviewDAOMapper;
    private final ReviewRepository reviewRepository;
    private final WebClient webClient;

    public TutorUserService(TutorUserRepository tutorUserRepository,
                            PasswordEncoder passwordEncoder, TutorUserDAOMapper tutorUserDAOMapper, TutorUserDTOMapper tutorUserDTOMapper,
                            UserPasswordValidator passwordValidator, ReviewDTOMapper reviewDTOMapper, ReviewDAOMapper reviewDAOMapper, ReviewRepository reviewRepository, WebClient webClient) {
        this.tutorUserRepository = tutorUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tutorUserDAOMapper = tutorUserDAOMapper;
        this.tutorUserDTOMapper = tutorUserDTOMapper;
        this.passwordValidator = passwordValidator;
        this.reviewDTOMapper = reviewDTOMapper;
        this.reviewDAOMapper = reviewDAOMapper;
        this.reviewRepository = reviewRepository;
        this.webClient = webClient;
    }

    /**
     * Method that is used to register valid tutor user.
     * @param userDTO DTO representation of the user
     * @throws IllegalArgumentException If DTO is null
     * @throws UserAlreadyExistsException if user with provided email address exists.
     */
    public void registerUser(UserRegistrationDTO userDTO) throws IllegalArgumentException, UserAlreadyExistsException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }

        if (checkIfTutorExists(userDTO.emailAddress())) {
            throw new UserAlreadyExistsException();
        }

        TutorUser domainUser = tutorUserDTOMapper.mapToDomain(userDTO);

        domainUser.unblock();

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), passwordValidator);

        TutorUserDAO tutor = tutorUserDAOMapper.mapToDAO(domainUser);

        tutorUserRepository.save(tutor);
    }

    /**
     * Method which is used to change tutor's user password.
     * @param id Tutor's user which will have password changed.
     * @param rawPassword new password
     */
    public void changePassword(String id, String rawPassword) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.changePassword(passwordEncoder.encode(rawPassword), passwordValidator);
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public TutorUserProfileDTO getTutorUserProfile(String id) {
        TutorUser tutor = getTutorUserById(id);
        return tutorUserDTOMapper.mapToProfile(getTutorUserById(id));
    }

    public void changeBiography(String id, String biography) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.changeBiography(biography);
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public void addSpecialization(String id, SchoolSubject specialization) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.addSpecializationItem(specialization);
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public void addSpecialization(String id, Collection<SchoolSubject> specializations) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.addSpecializationItem(specializations);
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public void removeSpecialization(String id, SchoolSubject specialization) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.removeSpecializationItem(specialization);
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public void removeSpecialization(String id, Collection<SchoolSubject> specializations) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.removeSpecializationItem(specializations);
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public void addReview(String id, ReviewCreationDTO reviewCreationDTO) {
        Review review = reviewDTOMapper.mapToDomain(reviewCreationDTO);

        reviewRepository.save(reviewDAOMapper.mapToDAO(review));

        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.addReview(reviewDTOMapper.mapToDomain(reviewCreationDTO));
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public void removeReview(String id, ReviewDTO reviewDTO) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.removeReview(reviewDTOMapper.mapToDomain(reviewDTO));
        reviewRepository.removeById(reviewDTO.id());
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    /**
     * Method that checks if user with provided email address exists.
     * @param emailAddress email address of user
     * @return boolean - true if exists, false otherwise.
     */
    private boolean checkIfTutorExists(String emailAddress) {
        Optional<TutorUserDAO> tutor = tutorUserRepository.findByEmailAddress(emailAddress);
        return tutor.isPresent();
    }

    private TutorUser getTutorUserById(String id) {
        Optional<TutorUserDAO> tutorFromDb = tutorUserRepository.findById(id);
        if (tutorFromDb.isEmpty()) throw new UserNotFoundException();
        return tutorUserDAOMapper.mapToDomain(tutorFromDb.get());
    }
}
