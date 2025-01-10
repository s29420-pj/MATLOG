package pl.pjatk.MATLOG.userManagement.tutorUser;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;
import pl.pjatk.MATLOG.Domain.Review;
import pl.pjatk.MATLOG.Domain.TutorUser;
import pl.pjatk.MATLOG.Domain.Exceptions.UserExceptions.*;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.ReviewDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.TutorUserProfileDTO;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.ReviewDTOMapper;
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
    private final PasswordEncoder passwordEncoder;
    private final TutorUserDAOMapper tutorUserDAOMapper;
    private final TutorUserDTOMapper tutorUserDTOMapper;
    private final UserPasswordValidator passwordValidator;
    private final ReviewDTOMapper reviewDTOMapper;

    public TutorUserService(TutorUserRepository tutorUserRepository,
                            PasswordEncoder passwordEncoder, TutorUserDAOMapper tutorUserDAOMapper, TutorUserDTOMapper tutorUserDTOMapper,
                            UserPasswordValidator passwordValidator, ReviewDTOMapper reviewDTOMapper) {
        this.tutorUserRepository = tutorUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tutorUserDAOMapper = tutorUserDAOMapper;
        this.tutorUserDTOMapper = tutorUserDTOMapper;
        this.passwordValidator = passwordValidator;
        this.reviewDTOMapper = reviewDTOMapper;
    }

    /**
     *
     * @param emailAddress Email address of the tutor user
     * @return TutorUser
     * @throws UsernameNotFoundException if tutor with provided email address has not been found
     * @throws UserInvalidEmailAddressException if provided email address is null or empty
     */
    public TutorUser findUserByEmailAddress(String emailAddress) throws UserInvalidEmailAddressException, UsernameNotFoundException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new UserInvalidEmailAddressException();
        }
        Optional<TutorUserDAO> userFromDatabase = tutorUserRepository.findByEmailAddress(emailAddress);
        if (userFromDatabase.isEmpty()) {
            throw new UserNotFoundException();
        }
        return tutorUserDAOMapper.mapToDomain(userFromDatabase.get());
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
     * @param tutorUser Tutor user which will have password changed.
     * @param rawPassword new password
     */
    public void changePassword(TutorUser tutorUser, String rawPassword) {
        tutorUser.changePassword(passwordEncoder.encode(rawPassword), passwordValidator);
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
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.addReview(reviewDTOMapper.mapToDomain(reviewCreationDTO));
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }

    public void removeReview(String id, ReviewDTO reviewDTO) {
        TutorUser tutorUser = getTutorUserById(id);
        tutorUser.removeReview(reviewDTOMapper.mapToDomain(reviewDTO));
        tutorUserRepository.save(tutorUserDAOMapper.mapToDAO(tutorUser));
    }
}
