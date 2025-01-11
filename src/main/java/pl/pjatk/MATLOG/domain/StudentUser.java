package pl.pjatk.MATLOG.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Concrete class representing Student in application
 */
public final class StudentUser extends User {

    /**
     * Method that returns builder and starts chaining creation of the object
     * @return StudentUserBuilder
     */
    public static StudentUserBuilder builder() {
        return new StudentUserBuilder();
    }

    /**
     * Concrete implementation of the Builder in User abstract class
     */
    public static class StudentUserBuilder extends Builder<StudentUserBuilder> {

        @Override
        protected StudentUserBuilder self() {
            return this;
        }

        /**
         * Sets user's role to STUDENT and builds the object.
         * @return StudentUser
         */
        @Override
        public StudentUser build() {
            return new StudentUser(this);
        }
    }

    /**
     * StudentUser constructor that creates object.
     * It adds authority as STUDENT_USER and sets role to Student.
     * @param builder StudentBuilder with set attributes
     */
    private StudentUser(StudentUserBuilder builder) {
        super(builder);
        if (!getAuthorities().contains(new SimpleGrantedAuthority("STUDENT_USER")))
            addAuthority(new SimpleGrantedAuthority("STUDENT_USER"));
    }

}
