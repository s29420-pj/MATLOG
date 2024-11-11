package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.pjatk.MATLOG.domain.exceptions.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Abstract class that represents User in application.
 * User is meant to be extended by all concrete classes that represents a role.
 *
 */
public abstract class User {
    @MongoId
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final LocalDate dateOfBirth;

    /**
     * Abstract builder that have to be extended in concrete User class
     * @param <T> - concrete user builder
     */
    abstract static class Builder<T extends Builder<T>> {
        private String firstName;
        private String lastName;
        private String emailAddress;
        private LocalDate dateOfBirth;

        /**
         * Method that sets User's first name
         * @param firstName - first name of the user
         * @return concrete Builder
         * @throws UserInvalidFirstNameException if first name is null or blank
         */
        public T withFirstName(String firstName) {
            if (firstName == null || firstName.isBlank()) {
                throw new UserInvalidFirstNameException();
            }
            this.firstName = firstName;
            return self();
        }

        /**
         * Method that sets User's last name
         * @param lastName - last name of the user
         * @return Builder
         * @throws UserInvalidLastNameException if last name is null or blank
         */
        public T withLastName(String lastName) {
            if (lastName == null || lastName.isBlank()) {
                throw new UserInvalidLastNameException();
            }
            this.lastName = lastName;
            return self();
        }

        /**
         * Method that sets User's email address
         * @param emailAddress - email address of the user
         * @return Builder
         * @throws UserInvalidEmailAddressException if email address is null or blank
         */
        public T withEmailAddress(String emailAddress) {
            if (emailAddress == null || emailAddress.isBlank()) {
                throw new UserInvalidEmailAddressException();
            }
            this.emailAddress = emailAddress;
            return self();
        }

        /**
         * Method that sets User's date of birth
         * @param dateOfBirth - date of birth of the user
         * @return Builder
         * @throws UserInvalidDateOfBirthException if date of birth is null or unreal ( x <= 0 or 100 < x)
         * @throws UserAgeRestrictionException if person who tries to register is under 13 years old
         * */
        public T withDateOfBirth(LocalDate dateOfBirth) {
            if (dateOfBirth == null) {
                throw new UserInvalidDateOfBirthException();
            }
            int age = LocalDate.now().getYear() - dateOfBirth.getYear();
            if (age <= 0 || age > 100) {
                throw new UserInvalidDateOfBirthException();
            }
            if (age < 13) {
                throw new UserAgeRestrictionException();
            }
            this.dateOfBirth = dateOfBirth;
            return self();
        }

        /**
         * Method that needs to be implemented by concrete builder static class
         * in the concrete User class
         * @return Builder
         */
        protected abstract T self();

        /**
         * Method that builds an objects with provided data
         * @return Object of a class that extends this class
         */
        protected abstract User build();
    }

    /**
     * Constructor of the User.
     * Sets a random UUID as an id of user and other data provided in builder.
     * @param builder builder of extended class
     * @throws UserInvalidFirstNameException if there is no first name or it's blank
     * @throws UserInvalidLastNameException if there is no last name or it's blank
     * @throws UserInvalidEmailAddressException if there is no email address or it's blank
     * @throws UserInvalidDateOfBirthException if there is no date of birth
     */
    protected User(Builder<?> builder) {
        if (builder.firstName == null || builder.firstName.isBlank()) {
            throw new UserInvalidFirstNameException();
        }
        if (builder.lastName == null  || builder.lastName.isBlank()) {
            throw new UserInvalidLastNameException();
        }
        if (builder.emailAddress == null || builder.emailAddress.isBlank()) {
            throw new UserInvalidEmailAddressException();
        }
        if (builder.dateOfBirth == null) {
            throw new UserInvalidDateOfBirthException();
        }
        this.id = UUID.randomUUID().toString();
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.dateOfBirth = builder.dateOfBirth;
    }

    /**
     * @return full name of user in format: "[first name] [last name]"
     */
    String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    String getEmailAddress() {
        return emailAddress;
    }

    String getId() {
        return id;
    }

}
