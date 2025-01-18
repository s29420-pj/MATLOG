package pl.pjatk.MATLOG.userManagement.tutorUser;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.domain.enums.SchoolSubject;
import pl.pjatk.MATLOG.domain.exceptions.userExceptions.UserEmptyPasswordException;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewDTO;
import pl.pjatk.MATLOG.userManagement.exceptions.TutorUserNotFoundException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserDTOMapper;
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
    private final TutorUserDAOMapper tutorUserDAOMapper;
    private final TutorUserDTOMapper tutorUserDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserPasswordValidator passwordValidator;
    private final ReviewService reviewService;

    public TutorUserService(TutorUserRepository tutorUserRepository,
                            PasswordEncoder passwordEncoder, TutorUserDAOMapper tutorUserDAOMapper, TutorUserDTOMapper tutorUserDTOMapper,
                            UserPasswordValidator passwordValidator, ReviewService reviewService) {
        this.tutorUserRepository = tutorUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tutorUserDAOMapper = tutorUserDAOMapper;
        this.tutorUserDTOMapper = tutorUserDTOMapper;
        this.passwordValidator = passwordValidator;
        this.reviewService = reviewService;
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
        if (rawPassword == null || rawPassword.isEmpty()) throw new UserEmptyPasswordException();
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.changePassword(passwordEncoder.encode(rawPassword), passwordValidator);
        save(tutorUser);
    }

    public TutorUserProfileDTO getTutorUserProfile(String id) {
        return tutorUserDTOMapper.mapToProfile(getTutorUserById(id));
    }

    public TutorUserProfileDTO getTutorUserProfileByEmailAddress(String emailAddress) {
        return tutorUserDTOMapper.mapToProfile(getTutorUserByEmailAddress(emailAddress));
    }

    public void changeBiography(String id, String biography) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.changeBiography(biography);
        save(tutorUser);
    }

    public void addSpecialization(String id, Collection<SchoolSubject> specializations) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.addSpecializationItem(specializations);
        save(tutorUser);
    }

    public void removeSpecialization(String id, Collection<SchoolSubject> specializations) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.removeSpecializationItem(specializations);
        save(tutorUser);
    }

    public void addReview(String tutorId, ReviewCreationDTO reviewCreationDTO) {
        TutorUser tutorUser = getTutorUserById(tutorId);
        tutorUser.addReview(reviewService.mapToDomain(reviewCreationDTO));
        save(tutorUser);
    }

    public void removeReview(String tutorId, String reviewId) {
        TutorUser tutorUser = getTutorUserById(tutorId);
        tutorUser.removeReview(reviewService.findReviewById(reviewId));
        save(tutorUser);
    }

    public void save(TutorUser tutorUser) {
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

    public TutorUser getTutorUserById(String id) {
        Optional<TutorUserDAO> tutorFromDb = tutorUserRepository.findById(id);
        if (tutorFromDb.isEmpty()) throw new TutorUserNotFoundException();
        return tutorUserDAOMapper.mapToDomain(tutorFromDb.get());
    }

    public TutorUser getTutorUserByEmailAddress(String emailAddress) {
        Optional<TutorUserDAO> tutorFromDB = tutorUserRepository.findByEmailAddress(emailAddress);
        if (tutorFromDB.isEmpty()) throw new TutorUserNotFoundException();
        return tutorUserDAOMapper.mapToDomain(tutorFromDB.get());
    }
}
