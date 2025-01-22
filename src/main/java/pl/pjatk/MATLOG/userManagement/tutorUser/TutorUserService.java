package pl.pjatk.MATLOG.userManagement.tutorUser;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pjatk.MATLOG.domain.TutorUser;
import pl.pjatk.MATLOG.domain.exceptions.userExceptions.UserEmptyPasswordException;
import pl.pjatk.MATLOG.reviewManagement.ReviewService;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewCreationDTO;
import pl.pjatk.MATLOG.reviewManagement.dto.ReviewRemoveDTO;
import pl.pjatk.MATLOG.userManagement.exceptions.InvalidPasswordException;
import pl.pjatk.MATLOG.userManagement.exceptions.TutorUserNotFoundException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserAlreadyExistsException;
import pl.pjatk.MATLOG.userManagement.exceptions.UserNotFoundException;
import pl.pjatk.MATLOG.userManagement.securityConfiguration.UserPasswordValidator;
import pl.pjatk.MATLOG.userManagement.tutorUser.dto.*;
import pl.pjatk.MATLOG.userManagement.tutorUser.mapper.TutorUserDTOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAO;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserDAOMapper;
import pl.pjatk.MATLOG.userManagement.tutorUser.persistance.TutorUserRepository;
import pl.pjatk.MATLOG.userManagement.user.dto.CredentialsDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.LoggedUserDTO;
import pl.pjatk.MATLOG.userManagement.user.dto.UserRegistrationDTO;

import java.util.List;
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
    public LoggedUserDTO registerUser(UserRegistrationDTO userDTO) throws IllegalArgumentException, UserAlreadyExistsException {
        if (userDTO == null) {
            throw new IllegalArgumentException("Please provide valid UserDTO");
        }

        if (checkIfTutorExists(userDTO.emailAddress())) {
            throw new UserAlreadyExistsException();
        }

        TutorUser domainUser = tutorUserDTOMapper.mapToDomain(userDTO);

        domainUser.unblock();

        domainUser.changePassword(passwordEncoder.encode(userDTO.password()), passwordValidator);

        save(domainUser);

        return login(new CredentialsDTO(userDTO.emailAddress(), userDTO.password()));
    }

    public LoggedUserDTO login(CredentialsDTO credentialsDTO) {
        TutorUserDAO tutorUserDAO = tutorUserRepository.findByEmailAddress(credentialsDTO.emailAddress())
                .orElseThrow(UserNotFoundException::new);

        TutorUser tutorUser = tutorUserDAOMapper.mapToDomain(tutorUserDAO);

        if (!passwordEncoder.matches(credentialsDTO.password(), tutorUser.getPassword())) {
            throw new InvalidPasswordException("Invalid password", HttpStatus.BAD_REQUEST);
        }

        return tutorUserDTOMapper.mapToLogin(tutorUser);
    }

    /**
     * Method which is used to change tutor's user password.
     * @param tutorUserChangePasswordDTO DTO representation of the tutor user with ID and new password.
     */
    public void changePassword(TutorUserChangePasswordDTO tutorUserChangePasswordDTO) {
        if (tutorUserChangePasswordDTO.rawPassword() == null || tutorUserChangePasswordDTO.rawPassword().isEmpty()) throw new UserEmptyPasswordException();
        TutorUser tutorUser = getTutorUserById(tutorUserChangePasswordDTO.id());
        tutorUser.changePassword(passwordEncoder.encode(tutorUserChangePasswordDTO.rawPassword()), passwordValidator);
        save(tutorUser);
    }

    public void changeEmailAddress(TutorUserChangeEmailAddressDTO tutorUserChangeEmailAddressDTO) {
        try {
            getTutorUserByEmailAddress(tutorUserChangeEmailAddressDTO.newEmailAddress());
        } catch (TutorUserNotFoundException ex) {
            TutorUser tutorUser = getTutorUserById(tutorUserChangeEmailAddressDTO.tutorId());
            tutorUser.changeEmailAddress(tutorUserChangeEmailAddressDTO.newEmailAddress());
            save(tutorUser);
        }
        throw new UserAlreadyExistsException();
    }

    public List<TutorUserProfileDTO> getAllTutors() {
        return tutorUserRepository.findAll().stream()
                .map(tutorUserDAOMapper::mapToDomain)
                .map(tutorUserDTOMapper::mapToProfile)
                .toList();
    }

    public TutorUserProfileDTO getTutorUserProfile(String id) {
        return tutorUserDTOMapper.mapToProfile(getTutorUserById(id));
    }

    public TutorUserProfileDTO getTutorUserProfileByEmailAddress(String emailAddress) {
        return tutorUserDTOMapper.mapToProfile(getTutorUserByEmailAddress(emailAddress));
    }

    public void changeBiography(TutorUserChangeBiographyDTO tutorUserChangeBiographyDTO) {
        TutorUser tutorUser = getTutorUserById(tutorUserChangeBiographyDTO.id());
        tutorUser.changeBiography(tutorUserChangeBiographyDTO.biography());
        save(tutorUser);
    }

    public void addSpecialization(TutorUserEditSpecializationDTO tutorUserEditSpecializationDTO) {
        TutorUser tutorUser = getTutorUserById(tutorUserEditSpecializationDTO.id());
        tutorUser.addSpecializationItem(tutorUserEditSpecializationDTO.specializations());
        save(tutorUser);
    }

    public void removeSpecialization(TutorUserEditSpecializationDTO tutorUserEditSpecializationDTO) {
        TutorUser tutorUser = getTutorUserById(tutorUserEditSpecializationDTO.id());
        tutorUser.removeSpecializationItem(tutorUserEditSpecializationDTO.specializations());
        save(tutorUser);
    }

    public void addReview(ReviewCreationDTO reviewCreationDTO) {
        TutorUser tutorUser = getTutorUserById(reviewCreationDTO.tutorId());
        tutorUser.addReview(reviewService.mapToDomain(reviewCreationDTO));
        save(tutorUser);
    }

    public void removeReview(ReviewRemoveDTO reviewRemoveDTO) {
        TutorUser tutorUser = getTutorUserById(reviewRemoveDTO.tutorId());
        tutorUser.removeReview(reviewService.findReviewById(reviewRemoveDTO.reviewId()));
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
