package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.pjatk.MATLOG.domain.exceptions.UserInvalidDataException;

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
         */
        public T withFirstName(String firstName) {
            if (firstName.isBlank()) {
                throw new UserInvalidDataException("Invalid data for first name. Please try again.");
            }
            this.firstName = firstName;
            return self();
        }

        /**
         * Method that sets User's last name
         * @param lastName - last name of the user
         * @return Builder
         */
        public T withLastName(String lastName) {
            if (lastName.isBlank()) {
                throw new UserInvalidDataException("Invalid data for last name. Please try again.");
            }
            this.lastName = lastName;
            return self();
        }

        /**
         * Method that sets User's email address
         * @param emailAddress - email address of the user
         * @return Builder
         */
        public T withEmailAddress(String emailAddress) {
            if (emailAddress.isBlank()) {
                throw new UserInvalidDataException("Invalid data for email address. Please try again.");
            }
            this.emailAddress = emailAddress;
            return self();
        }

        /**
         * Method that sets User's date of birth
         * @param dateOfBirth - date of birth of the user
         * @return Builder
         */
        public T withDateOfBirth(LocalDate dateOfBirth) {
            int age = LocalDate.now().getYear() - dateOfBirth.getYear();
            if (age <= 0 || age > 100) {
                throw new UserInvalidDataException(String.format("Invalid data for date of birth. Cannot consider %d real",
                        LocalDate.now().getYear() - dateOfBirth.getYear()));
            }
            if (age < 13) {
                throw new UserInvalidDataException("Sorry, you are too young to register. You must be at least 13 years old");
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

        protected abstract User build();
    }

    protected User(Builder<?> builder) {
        if (builder.firstName == null || builder.firstName.isBlank() ||
            builder.lastName == null  || builder.lastName.isBlank() ||
            builder.emailAddress == null || builder.emailAddress.isBlank() || builder.dateOfBirth == null) {
            throw new UserInvalidDataException("First name, last name, email address and date of birth" +
                    "canno be empty");
        }
        this.id = UUID.randomUUID().toString();
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.dateOfBirth = builder.dateOfBirth;
    }

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
