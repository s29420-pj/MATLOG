package pl.pjatk.MATLOG.domain;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.pjatk.MATLOG.domain.exceptions.userExceptions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Abstract class that represents User in application.
 * User is meant to be extended by all concrete classes that represents a role.
 */
public abstract class User {
    @MongoId
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String password;
    private final LocalDate dateOfBirth;

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
        validateFields(builder);
        this.id = UUID.randomUUID().toString();
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.dateOfBirth = builder.dateOfBirth;
    }

    private void validateFields(Builder<?> builder) {
        if (builder.firstName == null) throw new UserInvalidFirstNameException();
        if (builder.lastName == null) throw new UserInvalidLastNameException();
        if (builder.emailAddress == null) throw new UserInvalidEmailAddressException();
        if (builder.password == null) throw new UserEmptyPasswordException();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @return full name of user in format: "[first name] [last name]"
     */
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    /**
     * Method that calculates age of the user
     * @return age
     */
    public int getAge() {
        if (dateOfBirth == null) {
            throw new UserInvalidDateOfBirthException();
        }
        return (int)ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    /**
     * Abstract builder that have to be extended in concrete User class
     * @param <T> - concrete user builder
     */
    abstract static class Builder<T extends Builder<T>> {
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String password;
        private LocalDate dateOfBirth;

        private static final int MIN_AGE = 1;
        private static final int MAX_AGE = 100;

        /**
         * Method that sets User's first name
         * @param firstName first name of the user
         * @return Builder
         * @throws UserInvalidFirstNameException when first name is null or blank
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
         * @param lastName last name of the user
         * @return Builder
         * @throws UserInvalidLastNameException when last name is null or blank
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
         * @param emailAddress email address of the user
         * @return Builder
         * @throws UserInvalidEmailAddressException when email address is null or blank
         */
        public T withEmailAddress(String emailAddress) {
            if (emailAddress == null || emailAddress.isBlank()) {
                throw new UserInvalidEmailAddressException();
            }
            this.emailAddress = emailAddress;
            return self();
        }

        /**
         * Method that sets User's password.
         * @param password password of the user
         * @return Builder
         * @throws UserEmptyPasswordException when password is null or blank
         * @throws UserUnsecurePasswordException when password doesn't have any capital letter
         * nor special sign (33 - 64 in ASCII table) nor isn't at least 6 letters long
         */
        public T withPassword(String password) {
            if (password == null || password.isBlank()) {
                throw new UserEmptyPasswordException();
            }
            if (!UserPasswordValidator.isSecure(password)) {
                throw new UserUnsecurePasswordException();
            }
            this.password = password;
            return self();
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
            int age = (int)ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
            if (age < MIN_AGE || age > MAX_AGE) {
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

}
