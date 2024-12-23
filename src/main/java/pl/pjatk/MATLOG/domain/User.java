package pl.pjatk.MATLOG.Domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.Role;
import pl.pjatk.MATLOG.Domain.Exceptions.UserExceptions.*;
import pl.pjatk.MATLOG.UserManagement.securityConfiguration.UserPasswordValidator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract class that represents User in application.
 * User is meant to be extended by all concrete classes that want to represent a role.
 * Mandatory fields of User are:
 * - id, which is set by application
 * - first name, needs to be provided
 * - last name, needs to be provided
 * - email address, needs to be provided
 * - password, needs to be provided
 * - authorities, added USER and by concrete classes
 * - role, set by concrete classes
 */
public abstract class User {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private String password;
    private final LocalDate dateOfBirth;
    private final Set<GrantedAuthority> authorities;
    private boolean isAccountNonLocked;

    /**
     * Constructor of the User. Sets a random UUID as an id of user,
     * basic SimpleGrantedAuthority which is a USER role,
     * non-locked account, and other data provided in builder.
     * @param builder builder of extended class
     */
    protected User(Builder<?> builder) {
        validateFields(builder);
        if (builder.id == null || builder.id.isEmpty()) this.id = UUID.randomUUID().toString();
        else this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
        this.dateOfBirth = builder.dateOfBirth;
        this.authorities = Objects.requireNonNullElseGet(builder.authorities, HashSet::new);
        this.authorities.add(new SimpleGrantedAuthority("USER"));
        this.isAccountNonLocked = builder.isAccountNonLocked;
    }

    /**
     * Method that is being used by User constructor to check if mandatory fields are set.
     * @throws IllegalStateException if there is no first name, last name, email address, date of birth, or it's blank
     */
    private void validateFields(Builder<?> builder) {
        if (builder.firstName == null) throw new IllegalStateException("First name of user is mandatory and must be set");
        if (builder.lastName == null) throw new IllegalStateException("Last name of user is mandatory and must be set");
        if (builder.emailAddress == null) throw new IllegalStateException("Email address of user is mandatory and must be set");
        if (builder.password == null) throw new IllegalStateException("Password of user is mandatory and must be set");
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

    public void changePassword(String password, UserPasswordValidator passwordValidator) {
        if (password == null || password.isEmpty()) {
            throw new UserEmptyPasswordException();
        }
        if (!passwordValidator.isSecure(password)) throw new UserUnsecurePasswordException();
        this.password = password;
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

    public boolean addAuthority(GrantedAuthority authority) {
        return authorities.add(authority);
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
    public abstract static class Builder<T extends Builder<T>> {
        private String id;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String password;
        private LocalDate dateOfBirth;
        private Set<GrantedAuthority> authorities;
        private boolean isAccountNonLocked;

        private static final int MIN_AGE = 1;
        private static final int MAX_AGE = 100;

        /**
         * Method that sets user's id
         * @param id Identifier of a user.
         * @return Builder
         */
        public T withId(String id) {
            this.id = id;
            return self();
        }

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
         */
        public T withPassword(String password, UserPasswordValidator passwordValidator) {
            if (password == null || password.isBlank()) {
                throw new UserEmptyPasswordException();
            }
            if (!passwordValidator.isSecure(password)) {
                throw new UserUnsecurePasswordException();
            }
            this.password = password;
            return self();
        }

        /**
         * Method that sets User's date of birth
         * @param dateOfBirth - date of birth of the user
         * @return Builder
         * @throws UserInvalidDateOfBirthException if date of birth is unreal ( x <= 0 or 100 < x)
         * */
        public T withDateOfBirth(LocalDate dateOfBirth) {
            if (dateOfBirth != null) {
                int age = (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
                if (age < MIN_AGE || age > MAX_AGE) {
                    throw new UserInvalidDateOfBirthException();
                }
            }
            this.dateOfBirth = dateOfBirth;
            return self();
        }

        /**
         * Method that sets User's set of granted authorities
         * @param authorities authorities to add
         * @return Builder
         */
        public T withAuthorities(Set<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return self();
        }

        /**
         * Method that sets User's account status. It can be blocked or not
         * @param isAccountNonLocked status of an account
         * @return Builder
         */
        public T withIsAccountNonLocked(boolean isAccountNonLocked) {
            this.isAccountNonLocked = isAccountNonLocked;
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
