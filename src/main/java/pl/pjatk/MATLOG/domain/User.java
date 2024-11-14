package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.pjatk.MATLOG.domain.exceptions.userExceptions.*;

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

        public Builder(String firstName, String lastName, String emailAddress) {
            if (firstName == null || firstName.isBlank()) {
                throw new UserInvalidFirstNameException();
            }
            this.firstName = firstName;
            if (lastName == null || lastName.isBlank()) {
                throw new UserInvalidLastNameException();
            }
            this.lastName = lastName;
            if (emailAddress == null || emailAddress.isBlank()) {
                throw new UserInvalidEmailAddressException();
            }
            this.emailAddress = emailAddress;
        }

        /**
         * Method that sets User's date of birth
         * @param dateOfBirth - date of birth of the user
         * @return Builder
         * @throws UserInvalidDateOfBirthException if date of birth is null or unreal ( x <= 0 or 100 < x)
         * */
        public T withDateOfBirth(LocalDate dateOfBirth) {
            if (dateOfBirth == null) {
                throw new UserInvalidDateOfBirthException();
            }
            int age = LocalDate.now().getYear() - dateOfBirth.getYear();
            if (age <= 0 || age > 100) {
                throw new UserInvalidDateOfBirthException();
            }
            this.dateOfBirth = dateOfBirth;
            return self();
        }

        /**
         * Method that needs to be implemented by concrete builder static class
         * in the concrete User class. It must return builder class
         * @return Builder
         */
        abstract T self();

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
        this.id = UUID.randomUUID().toString();
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        if (builder.dateOfBirth != null) {
            this.dateOfBirth = builder.dateOfBirth;
        } else {
            this.dateOfBirth = null;
        }
    }

    /**
     * @return full name of user in format: "[first name] [last name]"
     */
    String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    int getAge() {
        if (dateOfBirth == null) {
            throw new UserInvalidDateOfBirthException();
        }
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    String getEmailAddress() {
        return emailAddress;
    }

    String getId() {
        return id;
    }

}
