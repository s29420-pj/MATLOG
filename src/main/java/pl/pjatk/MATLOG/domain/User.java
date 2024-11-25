package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.domain.exceptions.userExceptions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract class that represents User in application.
 * User is meant to be extended by all concrete classes that want to represent a role.
 * Mandatory fields of User are:
 * - id
 * - first name
 * - last name
 * - email address
 * - password
 */
public abstract class User {

    @MongoId
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String password;
    private final LocalDate dateOfBirth;
    private final Set<GrantedAuthority> authorities;
    private boolean isAccountNonLocked;
    private final Role role;

    /**
     * Constructor of the User. Sets a random UUID as an id of user, basic SimpleGrantedAuthority which is a USER role,
     * non-locked account, and other data provided in builder.
     * @param builder builder of extended class
     */
    protected User(Builder<?> builder) {
        validateFields(builder);
        this.id = UUID.randomUUID().toString();
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.dateOfBirth = builder.dateOfBirth;
        this.authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        this.isAccountNonLocked = true;
        this.role = builder.role;
    }

    /**
     * Method that is being used by User constructor to check if mandatory fields are set.
     * @throws IllegalStateException if there is no first name or it's blank
     * @throws IllegalStateException if there is no last name or it's blank
     * @throws IllegalStateException if there is no email address or it's blank
     * @throws IllegalStateException if there is no date of birth
     */
    private void validateFields(Builder<?> builder) {
        if (builder.firstName == null) throw new IllegalStateException("First name of user is mandatory and must be set");
        if (builder.lastName == null) throw new IllegalStateException("Last name of user is mandatory and must be set");
        if (builder.emailAddress == null) throw new IllegalStateException("Email address of user is mandatory and must be set");
        if (builder.password == null) throw new IllegalStateException("Password of user is mandatory and must be set");
        if (builder.role == null) builder.role = Role.STUDENT;
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

    public Set<GrantedAuthority> getAuthorities() {
        return Set.copyOf(authorities);
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    protected boolean addAuthority(GrantedAuthority authority) {
        return authorities.add(authority);
    }

    public Role getRole() {
        return role;
    }

    /**
     * @return full name of user in format: "[first name] [last name]"
     */
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    /**
     * Method that calculates age of the user
     * @return age or -1 if date of birth isn't provided
     */
    public int getAge() {
        if (dateOfBirth == null) {
            return -1;
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
        private Role role;

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
         * Method that sets User's role
         * @param role role of the user
         * @return Builder
         * @throws UserInvalidRoleException when role is not provided or there is not such a role
         */
        public T withRole(Role role) {
            if (role == null) {
                throw new UserInvalidRoleException();
            }
            this.role = role;
            return self();
        }

        /**
         * Method that needs to be implemented by concrete builder static class
         * in the concrete User class. It must return builder class
         * @return Builder
         */
        abstract T self();

        /**
         * Method that builds an objects with provided data and calls method that
         * validates if all the required fields was provided
         * @return User
         */
        protected abstract User build();
    }
}
